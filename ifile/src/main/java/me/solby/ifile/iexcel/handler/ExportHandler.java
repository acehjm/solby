package me.solby.ifile.iexcel.handler;

import me.solby.ifile.iexcel.ExcelMetaInfo;
import me.solby.ifile.iexcel.exception.ExcelException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static me.solby.ifile.iexcel.handler.CommonHandler.EXCEL_HSSF_SUFFIX;
import static me.solby.ifile.iexcel.handler.CommonHandler.EXCEL_XSSF_SUFFIX;

/**
 * @author majhdk
 * @DESCRIPTION 导出处理类
 * 【1。待添加自定义样式】
 * 【2。待添加从导入开始行】
 * @date 2018-12-02
 */
public class ExportHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExportHandler.class);

    /**
     * 写入浏览器输出流
     *
     * @param dataList 待写入数据
     * @param clz      Excel数据类
     * @param isXssf   写入Excel版本2003- or 2007+
     * @param fileName 文件名
     * @param response 响应体
     */
    public <T> void writeExcelRsp(List<T> dataList, Class<T> clz, boolean isXssf, String fileName,
                                  HttpServletResponse response) {
        try (  //try 这种写法会自动关闭流
               Workbook workbook = this.writeWorkbook(dataList, clz, isXssf);
               OutputStream os = response.getOutputStream()
        ) {
            String suffix = isXssf ? EXCEL_XSSF_SUFFIX : EXCEL_HSSF_SUFFIX;  //生成后缀
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment;filename="
                    + URLEncoder.encode(fileName + suffix, StandardCharsets.UTF_8));
            workbook.write(os);  //写入流
            os.flush();
        } catch (IOException e) {
            throw new ExcelException("Write response exception.", e);
        }
    }

    /**
     * 写入数据到本地缓存文件中
     *
     * @param path     写入Excel文件路径
     * @param dataList 待写入数据
     * @param clz      Excel数据类
     * @param isXssf   写入Excel版本2003- or 2007+
     */
    public <T> void writeExcelPath(String path, List<T> dataList, Class<T> clz, boolean isXssf) {
        try (
                Workbook workbook = this.writeWorkbook(dataList, clz, isXssf);
                FileOutputStream fos = new FileOutputStream(path)  //新建文件输出流
        ) {
            workbook.write(fos); //写入文件流
            fos.flush();
        } catch (FileNotFoundException e) {
            throw new ExcelException("File not found.", e);
        } catch (IOException e) {
            throw new ExcelException("Write file exception.", e);
        }
    }

    /**
     * 写入数据到Excel工作簿
     *
     * @param dataList 待写入数据
     * @param clz      Excel数据类
     * @param isXssf   写入Excel版本2003- or 2007+
     * @return
     */
    private <T> Workbook writeWorkbook(List<T> dataList, Class<T> clz, boolean isXssf) {
        Workbook workbook;
        if (isXssf) {
            workbook = new XSSFWorkbook();  //2007+
        } else {
            workbook = new HSSFWorkbook();  //2003-
        }
        Sheet sheet = workbook.createSheet();  //创建工作页
        Row row = sheet.createRow(0);
        List<ExcelMetaInfo> metaInfos = CommonHandler.getExcelMetaList(clz); //获取Excel数据类注解信息
        for (int i = 0; i < metaInfos.size(); i++) {  //for 设置第一行表头信息
            Cell cell = row.createCell(i);  //新增单元格
//            cell.setCellStyle(null);
            cell.setCellValue(metaInfos.get(i).getTitle());  //设置值
        }
        for (int i = 0; i < dataList.size(); i++) {  //写入数据
            row = sheet.createRow(i + 1);
            T obj = dataList.get(i);
            if (null != obj) {
                for (int j = 0; j < metaInfos.size(); j++) {  //匹配并设值
                    Cell cell = row.createCell(j);
                    cell.setCellType(CellType.STRING);  //单元格类型【待完善】
//                cell.setCellStyle(null);   //自定义样式
                    String value = getFieldValueByName(metaInfos.get(j).getFieldName(), obj); //获取值
                    cell.setCellValue(value);  //设值
                }
            }
        }
        return workbook;
    }

    /**
     * 根据属性名称获取属性值
     *
     * @param fieldName 属性名称
     * @param t         Excel数据类对象
     * @param <T>       泛型，Excel数据类对象
     * @return
     */
    private <T> String getFieldValueByName(String fieldName, T t) {
        Object value = null;
        try {
            Field field = this.getFieldByName(fieldName, t.getClass());  //根据名称获取属性
            if (field != null) {
                field.setAccessible(true);  //设置为可访问
                value = field.get(t);  //获取属性值
            }
        } catch (Exception e) {  //异常
            logger.error("Not found field");
            throw new ExcelException("Not found field");
        }
        return null == value ? StringUtils.EMPTY : String.valueOf(value);
    }

    /**
     * 根据名称获取属性
     *
     * @param fieldName 属性名称
     * @param clz       Excel数据类对象
     * @return
     */
    private Field getFieldByName(String fieldName, Class<?> clz) {
        Field[] fields = clz.getDeclaredFields();  //获取所有申明属性
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) { //按名称匹配
                return field;
            }
        }
        Class<?> superClz = clz.getSuperclass();  //检查父类
        if (superClz != null && superClz != Object.class) {
            return getFieldByName(fieldName, superClz);
        }
        return null;
    }


}
