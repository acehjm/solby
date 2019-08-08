package me.solby.ifile.iexcel.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.multipart.MultipartFile;

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
     * @param multipartFile 文件
     * @param type 对象类型
     * @param <T> T
     * @return
     */
    <T> List<T> readExcel(MultipartFile multipartFile, TypeReference<T> type);

}
