package me.solby.ifile.excel.service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * me.solby.ifile.iexcel.service.impl
 *
 * @author majhdk
 * @date 2019-08-08
 */
public interface ExcelWriter {

    /**
     * 写入浏览器输出流
     *
     * @param dataList 待写入数据
     * @param clz      Excel数据类
     * @param isXssf   写入Excel版本2003- or 2007+
     * @param fileName 文件名
     * @param response 响应体
     * @param <T>      T
     */
    <T> void writeExcelRsp(List<T> dataList, Class<T> clz, boolean isXssf, String fileName, HttpServletResponse response);

    /**
     * 写入数据到本地缓存文件中
     *
     * @param path     写入Excel文件路径
     * @param dataList 待写入数据
     * @param clz      Excel数据类
     * @param isXssf   写入Excel版本2003- or 2007+
     * @param <T>      T
     */
    <T> void writeExcelPath(String path, List<T> dataList, Class<T> clz, boolean isXssf);

}
