package me.solby.ifile.icsv.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.bean.concurrent.IntolerantThreadPoolExecutor;
import me.solby.ifile.icsv.annotation.CsvFileDesc;
import me.solby.ifile.icsv.constant.ValidFailType;
import me.solby.ifile.icsv.exception.CsvException;
import me.solby.ifile.icsv.serialize.CsvConverter;
import me.solby.ifile.icsv.serialize.FieldInformation;
import me.solby.ifile.icsv.validate.DataReadRecord;
import me.solby.ifile.icsv.validate.DataValidResult;
import me.solby.ifile.icsv.validate.DataValidator;
import me.solby.ifile.icsv.validate.DefaultDataValidator;
import org.springframework.util.Assert;

import java.io.*;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-12-01
 */
public class CsvReaderImpl implements ICsvReader {

    private ThreadPoolExecutor executor = new IntolerantThreadPoolExecutor();

    public CsvReaderImpl() {

    }

    @Override
    public <T> String read(File file, TypeReference<T> type, DataValidator<T> validator) {
        return this.doRead(file, type, null, validator);
    }

    @Override
    public <T> String read(File file, TypeReference<T> type, List<FieldInformation> infos, DataValidator<T> validator) {
        return this.doRead(file, type, infos, validator);
    }

    @Override
    public <T> DataReadRecord<T> getReadRecord(String progressCode) {
        //todo return getObject(progressCode);
        return null;
    }

    @Override
    public boolean removeRecord(String progressCode, boolean deleteFile) {
        DataReadRecord record = null;  //todo add cache
        if (record != null && record.getReadFile() != null && deleteFile) {
            //todo IOUtils.deleteFolderTree(record.getReadFile(), true);
        }
        return false;  //todo return memoryCache.remove();
    }

    private <T> String doRead(File file, TypeReference<T> type, List<FieldInformation> fieldInfos,
                              DataValidator<T> validator) {
        Class<?> tClass;
        FileInputStream fin;
        try (FileInputStream in = new FileInputStream(file)) {
            fin = in;
            tClass = Class.forName(type.getType().getTypeName());
            this.validAnnotation(tClass);
        } catch (ClassNotFoundException e) {
            throw new CsvException("class not found", e);
        } catch (IOException e) {
            throw new CsvException("file not found", e);
        }

        DataValidator<T> realValidator;
        realValidator = Objects.requireNonNullElseGet(validator, DefaultDataValidator::new);

        String progressCode = this.generateProgressCode();
        List infos;
        if (fieldInfos == null) {
            infos = CsvConverter.getFieldInformation(tClass);
        } else {
            infos = fieldInfos;
        }

        int ignoreRows = this.getIgnoreRows(tClass, file);
        String charset = this.getCharset(tClass);
        String spliter = this.getColumnSpliter(tClass);
        Character character = spliter.toCharArray()[0];
        boolean retired = false;

        int totalLine;
        try {
            totalLine = this.getTotalLine(file, ignoreRows, charset);
        } catch (Exception e) {
            throw new CsvException("get file total line error", e);
        }

        DataReadRecord<T> record = new DataReadRecord<>(progressCode, totalLine);
        record.setReadFile(file);

        //todo this.memoryCache.setObject(record.getProgressCode(), record, 120000L);
        //todo pool executor
        executor.execute(() -> {
            record.setRunStatus(0);
            try (CSVReader reader = this.getReader(fin, charset)) {
                List<String[]> allLines = reader.readAll();
                for (int rowNo = 1, i = 0; i < allLines.size(); rowNo++, i++) {
                    String line = this.generateStringFromArray(allLines.get(i), character);
                    if (rowNo <= ignoreRows) {
                        record.appendHead(line);
                    } else {
                        try {
                            T obj = (T) CsvConverter.toObject(allLines.get(i), type, infos);
                            DataValidResult<T> result = new DataValidResult<>(rowNo, line, obj);
                            if (!realValidator.valid(result, record)) {
                                record.addResult(result);
                                record.setRunStatus(3);
                                break;
                            }
                            record.addResult(result);
                        } catch (Exception e) {
                            record.addResult(new DataValidResult<>(rowNo, line,
                                    ValidFailType.DeserializeError, "read object error"));
                        }
                    }
                }
                record.setRunStatus(2);
            } catch (Exception e) {
                record.setRunStatus(3);
            } finally {
                record.setFinishTime(Calendar.getInstance().getTimeInMillis());
            }
        });
        return progressCode;
    }

    private String generateStringFromArray(String[] strArray, Character character) {
        StringBuilder sb = new StringBuilder();
        for (String s : strArray) {
            sb.append(s).append(character);
        }
        return sb.toString();
    }

//    private int headSize(File file, String charset) {
//        return "UTF-8".equals(charset.toUpperCase()) && IOUtils.withBOM(file) ? 3: 0;
//    }

    private String generateProgressCode() {
        return UUID.randomUUID().toString();
    }

    private <T> void validAnnotation(Class<T> elementClass) {
        CsvFileDesc cfd = elementClass.getAnnotation(CsvFileDesc.class);
        Assert.notNull(cfd, "valid @CsvFileDesc is null");
    }

    private <T> int getIgnoreRows(Class<T> elementClass, File file) {
        return Integer.valueOf(elementClass.getAnnotation(CsvFileDesc.class).ignoreRows());
    }

    private <T> String getCharset(Class<T> elementClass) {
        return elementClass.getAnnotation(CsvFileDesc.class).charset();
    }

    private <T> String getColumnSpliter(Class<T> elementClass) {
        return elementClass.getAnnotation(CsvFileDesc.class).columnSpliter();
    }

    private CSVReader getReader(InputStream is, String charset) {
        RFC4180Parser parser = new RFC4180ParserBuilder().build();
        try {
            return new CSVReaderBuilder(new InputStreamReader(is, charset)).withCSVParser(parser).build();
        } catch (UnsupportedEncodingException e) {
            throw new CsvException("unsupported encoding", e);
        }
    }

    private int getTotalLine(File file, int ignoreRows, String charset) throws IOException {
        int total = -ignoreRows;
        try (
                InputStream in = new FileInputStream(file);
                CSVReader reader = this.getReader(in, charset)
        ) {
            List<String[]> allLines = reader.readAll();
            if (null != allLines) {
                total += allLines.size();
            } else {
                total = 0;
            }
        }
        return total;
    }
}
