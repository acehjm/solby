package me.solby.xfile.csv.service.impl;

import com.opencsv.bean.BeanVerifier;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import me.solby.xfile.csv.annotation.CsvFileDesc;
import me.solby.xfile.csv.service.CsvReader;
import me.solby.xfile.excel.exception.ExcelException;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * me.solby.ifile.icsv.service
 *
 * @author majhdk
 * @date 2019-07-31
 */
public class CsvReaderImpl implements CsvReader {

    @Override
    public <T> List<T> read(File file, Class<T> type) {
        return this.doRead(file, type, null);
    }

    @Override
    public <T> List<T> read(File file, Class<T> type, BeanVerifier<T> verifier) {
        return this.doRead(file, type, verifier);
    }

    /**
     * 读取解析文件
     *
     * @param file     文件
     * @param type     目标对象
     * @param verifier 校验
     * @param <T>      T
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> List<T> doRead(File file, Class<T> type, BeanVerifier<T> verifier) {
        Class<?> tClass;
        try {
            tClass = Class.forName(type.getTypeName());  //获取Excel数据类
        } catch (ClassNotFoundException e) {
            throw new ExcelException("Class not found", e);
        }
        // 校验目标对象注解是否为空
        this.validAnnotation(tClass);

        try (FileReader fileReader = new FileReader(file, Charset.forName(getCharset(tClass)))) {
            CsvToBeanBuilder builder = new CsvToBeanBuilder(fileReader)
                    .withSkipLines(getSkipLines(tClass))
                    .withSeparator(getSeparator(tClass))
                    .withFieldAsNull(CSVReaderNullFieldIndicator.NEITHER)
//                    .withFilter(CsvToBeanFilter)
                    .withType(tClass);

            if (null != verifier) {
                builder.withVerifier(verifier);
            }

            return builder.build().parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 获取忽略行
     *
     * @param elementClass 目标对象
     * @param <T>          T
     * @return
     */
    private <T> int getSkipLines(Class<T> elementClass) {
        return elementClass.getAnnotation(CsvFileDesc.class).skipLines();
    }

    /**
     * 获取分隔符
     *
     * @param elementClass 目标对象
     * @param <T>          T
     * @return
     */
    private <T> char getSeparator(Class<T> elementClass) {
        return elementClass.getAnnotation(CsvFileDesc.class).separator();
    }

    /**
     * 获取字符集
     *
     * @param elementClass 目标对象
     * @param <T>          T
     * @return
     */
    private <T> String getCharset(Class<T> elementClass) {
        return elementClass.getAnnotation(CsvFileDesc.class).charset();
    }

    /**
     * 校验注解
     *
     * @param elementClass 目标对象
     * @param <T>          T
     */
    private <T> void validAnnotation(Class<T> elementClass) {
        CsvFileDesc cfd = elementClass.getAnnotation(CsvFileDesc.class);
        Assert.notNull(cfd, "Annotation @CsvFileDesc not exists");
    }

}
