package me.solby.ifile.excel.service.impl;

import me.solby.ifile.excel.annotation.ExcelFileDesc;
import me.solby.ifile.excel.common.CommonHandler;
import me.solby.ifile.excel.common.ExcelFormatEnum;
import me.solby.ifile.excel.exception.ExcelException;
import me.solby.ifile.excel.model.TargetMetaInfo;
import me.solby.ifile.excel.service.ExcelReader;
import me.solby.itool.json.JsonUtil;
import me.solby.itool.verify.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author majhdk
 * @DESCRIPTION 导入处理类
 * 【1。后续继续完善】
 * 【2。后期添加缓存功能】
 * 【3。是否添加导入最大行限制】
 * 【4。后期添加数据校验功能】
 * @date 2018-12-02
 */
public class ExcelReaderImpl implements ExcelReader {

    /**
     * 读取Excel数据到Excel数据类对象中
     *
     * @param fileName    文件名
     * @param inputStream 输入流
     * @param type        Excel数据类
     * @param <T>         泛型，Excel数据类
     * @return
     */
    @Override
    public <T> List<T> readExcel(String fileName, InputStream inputStream, Class<T> type) {
        String suffix = ObjectUtil.isEmpty(fileName) ? "" : fileName.substring(fileName.indexOf('.'));
        //获取Excel工作簿
        Workbook workbook = ExcelFormatEnum.suffixOf(suffix).getWorkBook(inputStream);

        return this.getExcelDataList(type, workbook);
    }

    /**
     * 获取Excel数据到集合
     *
     * @param type     目标类
     * @param workbook 工作簿
     * @return
     */
    private <T> List<T> getExcelDataList(Class<T> type, Workbook workbook) {
        Class<?> tClass = this.getTargetClass(type);

        if (null == workbook) {
            throw new ExcelException("workbook is null");
        }

        try (workbook) {
            // 获取当前sheet工作表【默认只有一个表工作，不支持多个】
            Sheet sheet = workbook.getSheetAt(0);
            if (null == sheet || sheet.getPhysicalNumberOfRows() <= 1) {
                throw new ExcelException("No valid data of file");
            }

            List<TargetMetaInfo> metaInfos = this.fillTargetMateInfo(sheet.getRow(this.getSkipLines(tClass)), tClass);

            int firstCellNum = metaInfos.stream()
                    .map(TargetMetaInfo::getColumnPosition)
                    .min(Comparator.comparingInt(p -> p))
                    .orElse((int) sheet.getRow(0).getFirstCellNum());
            int lastCellNum = metaInfos.stream()
                    .map(TargetMetaInfo::getColumnPosition)
                    .max(Comparator.comparing(p -> p))
                    .orElse((int) sheet.getRow(0).getLastCellNum());

            final List<String[]> cellList = this.getCellValueList(tClass, sheet, firstCellNum, lastCellNum);

            return cellList.stream()
                    .map(line -> this.buildBean(line, type, metaInfos))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ExcelException("parse excel error ", e);
        }
    }

    /**
     * 获取所有数据行单元格值列表
     *
     * @param tClass       目标类
     * @param sheet        页
     * @param firstCellNum 第一列
     * @param lastCellNum  最后一列
     * @return
     */
    private List<String[]> getCellValueList(Class<?> tClass, Sheet sheet, int firstCellNum, int lastCellNum) {
        int skipLines = this.getSkipLines(tClass);
        // 获取忽略行或开始行
        int firstRowNum = skipLines + 1;
        // 获取当前sheet的结束行
        int lastRowNum = sheet.getLastRowNum();
        // 所有数据行
        List<String[]> cellList = new ArrayList<>(lastRowNum - firstRowNum);

        // 循环所有数据行
        for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (null == row) {
                continue;
            }

            String[] cells = new String[sheet.getRow(skipLines).getPhysicalNumberOfCells()];
            // 循环当前行所有列
            for (int cellNum = firstCellNum; cellNum <= lastCellNum; cellNum++) {
                Cell cell = row.getCell(cellNum);
                cells[cellNum] = this.getCellValue(cell);
            }
            cellList.add(cells);
        }
        return cellList;
    }

    /**
     * 生成Excel数据类信息
     * 使用Json转换方式，非反射方式
     *
     * @param line  Excel行数据
     * @param type  Excel数据类
     * @param infos Excel元数据
     * @param <T>   泛型，Excel数据类
     * @return
     */
    private <T> T buildBean(String[] line, Class<T> type, List<TargetMetaInfo> infos) {
        if (null == line || 0 == line.length || null == infos || infos.isEmpty()) {
            return null;
        }

        Map<String, String> map = new HashMap<>(); //数据集合
        for (TargetMetaInfo info : infos) {  //Excel元数据
            map.put(info.getFieldName(), line[info.getColumnPosition()].trim());
        }
        return JsonUtil.mapToPojo(map, type);  //转换为Excel数据类对象
    }

    /**
     * 填充导入对象愿数据
     *
     * @param titleRow 标题行
     * @param tClass   目标类
     * @return
     */
    private List<TargetMetaInfo> fillTargetMateInfo(final Row titleRow, final Class<?> tClass) {
        List<TargetMetaInfo> metaInfos = CommonHandler.getExcelMetaList(tClass);
        // 校验标题行是否有误
        boolean existsName = metaInfos.stream()
                .anyMatch(it -> it.getColumn().equals(this.getCellValue(titleRow.getCell(titleRow.getFirstCellNum()))));
        if (null == titleRow || !existsName) {
            throw new ExcelException("Excel sheet have not column name");
        }

        // 获取标题行数据
        List<Cell> cells = new ArrayList<>(titleRow.getLastCellNum() - titleRow.getFirstCellNum());
        for (int cellNum = titleRow.getFirstCellNum(); cellNum < titleRow.getLastCellNum(); cellNum++) {
            Cell cell = titleRow.getCell(cellNum);
            cells.add(cell);
        }
        // 填充元数据
        metaInfos.forEach(targetMetaInfo -> cells.stream()
                .filter(cell -> cell.getStringCellValue().equals(targetMetaInfo.getColumn()))
                .findFirst()
                .ifPresent(cell -> targetMetaInfo.setColumnPosition(cell.getColumnIndex())));
        return metaInfos;
    }

    /**
     * 获取单元格值【目前仅支持字符串类型和数值类型，后续支持更多类型】
     *
     * @param cell 单元格
     * @return
     */
    private String getCellValue(Cell cell) {
        String cellValue;
        CellType cellType = cell.getCellType();
        if (cellType.equals(CellType.STRING)) {
            cellValue = cell.getRichStringCellValue().getString().trim();
        } else if (cellType.equals(CellType.NUMERIC)) {
            cellValue = new DecimalFormat("#").format(cell.getNumericCellValue());
        } else {
            cellValue = StringUtils.EMPTY;
        }
        return cellValue;
    }

    /**
     * 获取目标类
     *
     * @param type 范型类
     * @param <T>  T
     * @return
     */
    private <T> Class<?> getTargetClass(Class<T> type) {
        Class<?> tClass;
        try {
            tClass = Class.forName(type.getTypeName());  //获取Excel数据类
        } catch (ClassNotFoundException e) {
            throw new ExcelException("Class not found", e);
        }
        return tClass;
    }

    /**
     * 获取开始行
     *
     * @param clz Excel数据类
     * @param <T> 泛型，Excel数据类
     * @return
     */
    private <T> int getSkipLines(Class<T> clz) {
        return clz.getAnnotation(ExcelFileDesc.class).skipLines();
    }

}
