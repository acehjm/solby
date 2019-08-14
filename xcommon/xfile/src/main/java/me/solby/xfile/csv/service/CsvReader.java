package me.solby.xfile.csv.service;

import com.opencsv.bean.BeanVerifier;

import java.io.File;
import java.util.List;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-12-01
 */
public interface CsvReader {

    /**
     * 读取文件记录到对象
     *
     * @param file     文件
     * @param type     目标对象
     * @param verifier 对象校验
     * @param <T>      范型
     * @return
     */
    <T> List<T> read(File file, Class<T> type, BeanVerifier<T> verifier);

    /**
     * 读取文件记录到对象
     *
     * @param file 文件
     * @param type 目标对象
     * @param <T>  范型
     * @return
     */
    <T> List<T> read(File file, Class<T> type);

}
