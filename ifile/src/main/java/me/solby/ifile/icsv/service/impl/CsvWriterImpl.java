package me.solby.ifile.icsv.service.impl;

import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import me.solby.ifile.icsv.annotation.CsvFileDesc;
import me.solby.ifile.icsv.exception.CsvException;
import me.solby.ifile.icsv.service.CsvWriter;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

/**
 * me.solby.ifile.icsv.service
 *
 * @author majhdk
 * @date 2019-08-07
 */
public class CsvWriterImpl implements CsvWriter {

    @Override
    public <T> void write(List<T> data, String fileName, Class<T> type, HttpServletResponse response) {
        // 校验目标对象注解是否为空
        this.validAnnotation(type);
        try {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment;filename="
                    + URLEncoder.encode(fileName, Charset.forName(getCharset(type))));

            OutputStream outputStream = response.getOutputStream();
            this.doWriter(data, type, new OutputStreamWriter(outputStream, Charset.forName(getCharset(type))));
        } catch (IOException e) {
            throw new CsvException("IO Exception.", e);
        }
    }

    @Override
    public <T> void write(List<T> data, String filePath, Class<T> type) {
        // 校验目标对象注解是否为空
        this.validAnnotation(type);
        try {
            this.doWriter(data, type, new FileWriter(filePath, Charset.defaultCharset()));
        } catch (IOException e) {
            throw new CsvException("IO Exception.", e);
        }
    }

    /**
     * 处理Bean转换到CSV中
     *
     * @param data   数据集
     * @param type   数据类型
     * @param writer writer
     * @param <T>    T
     */
    @SuppressWarnings("unchecked")
    private <T> void doWriter(List<T> data, Class<T> type, Writer writer) {
        try (writer) {
            HeaderColumnNameMappingStrategy strategy = this.mappingStrategy(type);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withMappingStrategy(strategy)  // 设置将Bean写入CSV的映射策略
                    .withApplyQuotesToAll(false)  // 是否将所有输出放在引号中
                    .withSeparator(getSeparator(type))
                    .build();
            beanToCsv.write(data);
        } catch (IOException e) {
            throw new CsvException("IO Exception.", e);
        } catch (CsvRequiredFieldEmptyException e) {
            throw new CsvException("CsvData required filed empty.", e);
        } catch (CsvDataTypeMismatchException e) {
            throw new CsvException("CsvData type mismatch.", e);
        }
    }

    /**
     * 配置映射策略
     *
     * @param type
     * @param <T>
     * @return
     */
    private <T> HeaderColumnNameMappingStrategy mappingStrategy(Class<T> type) {
        HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(type);
        // TODO 添加排序
        //  strategy.setColumnOrderOnWrite(new MyComparator());
        return strategy;
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
