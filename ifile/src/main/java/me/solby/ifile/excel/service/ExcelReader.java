package me.solby.ifile.excel.service;

import java.io.InputStream;
import java.util.List;

/**
 * me.solby.ifile.iexcel.service
 *
 * @author majhdk
 * @date 2019-08-08
 */
public interface ExcelReader {

    /**
     * 读取Excel文件内容
     *
     * @param fileName    文件名
     * @param inputStream 输入流
     * @param type        对象类型
     * @param <T>         T
     * @return
     */
    <T> List<T> readExcel(String fileName, InputStream inputStream, Class<T> type);

}
