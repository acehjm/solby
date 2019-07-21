package me.solby.ifile.iexcel.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import me.solby.ifile.iexcel.ExcelMetaInfo;
import me.solby.ifile.iexcel.annotation.ExcelFileDesc;
import me.solby.ifile.iexcel.exception.ExcelException;
import me.solby.itool.json.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.solby.ifile.iexcel.handler.CommonHandler.EXCEL_HSSF_SUFFIX;
import static me.solby.ifile.iexcel.handler.CommonHandler.EXCEL_XSSF_SUFFIX;

/**
 * @author majhdk
 * @DESCRIPTION 导入处理类
 * 【1。后续继续完善】
 * 【2。后期添加缓存功能】
 * 【3。是否添加导入最大行限制】
 * 【4。后期添加数据校验功能】
 * @date 2018-12-02
 */
public class ImportHandler {

    private static final Logger logger = LoggerFactory.getLogger(ImportHandler.class);

    /**
     * 导入文件最大值
     */
    private static final Integer EXCEL_MAXIMUM = 10 * 1024 * 1024;
    /**
     * 导入文件最小值
     */
    private static final Integer EXCEL_MINIMUM = 0;

    /**
     * 读取Excel数据到Excel数据类对象中
     *
     * @param multipartFile Excel文件
     * @param type          Excel数据类
     * @param <T>           泛型，Excel数据类
     * @return
     */
    public <T> List<T> readExcel(MultipartFile multipartFile, TypeReference<T> type) {
        //校验文件的有效性
        this.checkFile(multipartFile);
        //获取Excel工作簿
        Workbook workbook = this.getWorkbook(multipartFile);

        List<T> list = new ArrayList<>();  //所有的Excel有效数据
        Class<?> tClass;
        try {
            tClass = Class.forName(type.getType().getTypeName());  //获取Excel数据类
        } catch (ClassNotFoundException e) {
            throw new ExcelException("Class not found", e);
        }
        int ignoreRows = getIgnoreRows(tClass);  //Excel数据类注解，获取忽略行或开始行

        List<String[]> lines = this.getExcelDataList(workbook, ignoreRows);  //解析数据到集合中
        for (String[] line : lines) {
            T obj = this.buildPojo(line, type, CommonHandler.getExcelMetaList(tClass));  //生成Excel数据类
            if (null != obj) {
                list.add(obj);  //添加到集合
            }
        }
        return list;
    }

    /**
     * 生成Excel数据类信息
     *
     * @param lines Excel行数据
     * @param type  Excel数据类
     * @param infos Excel元数据
     * @param <T>   泛型，Excel数据类
     * @return
     */
    private <T> T buildPojo(String[] lines, TypeReference<T> type, List<ExcelMetaInfo> infos) {
        if (lines != null && lines.length != 0 && infos != null && !infos.isEmpty()) { //判断参数是否有效
            Map<String, String> map = new HashMap<>(); //数据集合
            for (ExcelMetaInfo info : infos) {  //Excel元数据
                if (info.getOrder() <= lines.length) { //根据order匹配名称和值【order必须连续】
                    map.put(info.getFieldName(), lines[info.getOrder() - 1].trim());
                }
            }
            return JsonUtil.toPojo(JsonUtil.toJson(map), type);  //转换为Excel数据类对象
        }
        return null;
    }

    /**
     * 获取Excel数据到集合
     *
     * @param workbook    工作簿
     * @param firstRowNum 开始行，也是忽略行
     * @return
     */
    private List<String[]> getExcelDataList(Workbook workbook, int firstRowNum) {
        List<String[]> list = new ArrayList<>();
        if (null != workbook) {
            //获取当前sheet工作表【默认只有一个表工作，不支持多个】
            Sheet sheet = workbook.getSheetAt(0);
            if (null == sheet || sheet.getPhysicalNumberOfRows() <= 1) {
                throw new ExcelException("No valid data of file");
            }

            //int firstRowNum = sheet.getFirstRowNum();
            //firstRowNum -> 获取当前sheet的开始行
            int lastRowNum = sheet.getLastRowNum();  //获取当前sheet的结束行

            int firstCellNum = sheet.getRow(0).getFirstCellNum();  //获取当前行的开始列
            int lastCellNum = sheet.getRow(0).getLastCellNum();   //获取当前行的结束列

            //循环除了第一行的所有行
            for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
                Row row = sheet.getRow(rowNum);   //获取当前行
                if (null == row) continue;   //当前行为空，继续下一行

                String[] cells = new String[lastCellNum];
                //循环当前行
                for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                    Cell cell = row.getCell(cellNum);
                    cells[cellNum] = getCellValue(cell);  //根据类型获取单元格值
                }
                list.add(cells);  //添加到集合
            }
            try {
                workbook.close();  //关闭工作簿
            } catch (IOException e) {
                logger.error("An exception occurred while closing the workbook", e);
            }
        }
        return list;
    }

    /**
     * 获取Excel数据类类注解
     *
     * @param clz Excel数据类
     * @param <T> 泛型，Excel数据类
     * @return
     */
    private <T> int getIgnoreRows(Class<T> clz) {
        return clz.getAnnotation(ExcelFileDesc.class).ignoreRows();
    }

    /**
     * 获取Excel工作簿
     *
     * @param multipartFile Excel文件
     * @return
     */
    private Workbook getWorkbook(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        fileName = fileName == null ? "" : fileName;
        Workbook workbook = null;
        try {
            InputStream is = multipartFile.getInputStream();
            if (fileName.endsWith(EXCEL_HSSF_SUFFIX)) {
                workbook = new HSSFWorkbook(is);  //2003及以下版本
            } else if (fileName.endsWith(EXCEL_XSSF_SUFFIX)) {
                workbook = new XSSFWorkbook(is);  //2007及更高版本
            }
        } catch (IOException e) {
            throw new ExcelException("file io exception", e);
        }
        return workbook;
    }

    /**
     * 校验导入文件
     *
     * @param multipartFile Excel文件
     */
    private void checkFile(MultipartFile multipartFile) {
        if (null == multipartFile) {  //校验文件是否为空
            logger.error("Excel file does not exist");
            throw new ExcelException("Excel file does not exist");
        }
        String name = multipartFile.getOriginalFilename();
        name = name == null ? "" : name;  //避免空指针异常
        if (!name.endsWith(EXCEL_HSSF_SUFFIX) && !name.endsWith(EXCEL_XSSF_SUFFIX)) {//校验文件是否Excel文件
            logger.error("Not a valid Excel file");
            throw new ExcelException("Not a valid Excel file");
        }
        if (multipartFile.getSize() <= EXCEL_MINIMUM) {  //校验文件大小
            logger.error("Excel file is empty");
            throw new ExcelException("Excel file is empty");
        }
        if (multipartFile.getSize() > EXCEL_MAXIMUM) {  //校验文件大小
            logger.error("The maximum file limit is " + EXCEL_MAXIMUM);
            throw new ExcelException("The maximum file limit is " + EXCEL_MAXIMUM);
        }
    }

    /**
     * 获取单元格值【目前仅支持字符串类型和数值类型，后续支持更多类型】
     *
     * @param cell 单元格
     * @return
     */
    private String getCellValue(Cell cell) {
        String cellValue;
        CellType cellType = cell.getCellTypeEnum();
        if (cellType.equals(CellType.STRING)) {
            cellValue = cell.getRichStringCellValue().getString().trim();
        } else if (cellType.equals(CellType.NUMERIC)) {
            cellValue = new DecimalFormat("#").format(cell.getNumericCellValue());
        } else {
            cellValue = StringUtils.EMPTY;
        }
        return cellValue;
    }

}
