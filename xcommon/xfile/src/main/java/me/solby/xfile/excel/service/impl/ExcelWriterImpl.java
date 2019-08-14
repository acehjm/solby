package me.solby.xfile.excel.service.impl;

import me.solby.xfile.excel.common.CommonHandler;
import me.solby.xfile.excel.common.ExcelFormatEnum;
import me.solby.xfile.excel.exception.ExcelException;
import me.solby.xfile.excel.model.TargetMetaInfo;
import me.solby.xfile.excel.service.ExcelWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

/**
 * @author majhdk
 * @DESCRIPTION 导出处理类
 * 【1。待添加自定义样式】
 * 【2。待添加从导入开始行】
 * @date 2018-12-02
 */
public class ExcelWriterImpl implements ExcelWriter {

    /**
     * 写入浏览器输出流
     *
     * @param dataList 待写入数据
     * @param clz      Excel数据类
     * @param isXssf   写入Excel版本2003- or 2007+
     * @param fileName 文件名
     * @param response 响应体
     */
    @Override
    public <T> void writeExcelRsp(List<T> dataList, Class<T> clz, boolean isXssf, String fileName,
                                  HttpServletResponse response) {
        // try 这种写法会自动关闭流
        try (
                Workbook workbook = this.writeWorkbook(dataList, clz, isXssf);
                OutputStream os = response.getOutputStream()
        ) {
            String suffix = isXssf ? ExcelFormatEnum.EXCEL_XSSF.getSuffix() : ExcelFormatEnum.EXCEL_HSSF.getSuffix();  //生成后缀
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
    @Override
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
        // xssf-2007+ ; hssf-2003-
        Workbook workbook = isXssf ? new XSSFWorkbook() : new HSSFWorkbook();

        // 创建工作页
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        // 获取Excel数据类注解信息
        List<TargetMetaInfo> metaInfos = CommonHandler.getExcelMetaList(clz);
        // 设置第一行表头信息
        for (int i = 0; i < metaInfos.size(); i++) {
            Cell cell = row.createCell(i);  //新增单元格
//            cell.setCellStyle(null);
            cell.setCellValue(metaInfos.get(i).getColumn());  //设置值
        }

        for (int i = 0; i < dataList.size(); i++) {  //写入数据
            T bean = dataList.get(i);
            if (null == bean) {
                continue;
            }

            row = sheet.createRow(i + 1);
            for (int j = 0; j < metaInfos.size(); j++) {  //匹配并设值
                Cell cell = row.createCell(j);
//                cell.setCellStyle(null);   //自定义样式
                String value = this.getFieldValueByName(metaInfos.get(j).getFieldName(), bean); //获取值
                cell.setCellValue(value);  //设值
            }
        }
        return workbook;
    }

    /**
     * 根据属性名称获取属性值
     *
     * @param fieldName 属性名称
     * @param bean      Excel数据类对象
     * @param <T>       泛型，Excel数据类对象
     * @return
     */
    private <T> String getFieldValueByName(String fieldName, T bean) {
        Object value = null;
        try {
            Field field = this.getFieldByName(fieldName, bean.getClass());  //根据名称获取属性
            if (field != null) {
                field.setAccessible(true);  //设置为可访问
                value = field.get(bean);  //获取属性值
            }
        } catch (Exception e) {  //异常
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
        return null;
    }


}
