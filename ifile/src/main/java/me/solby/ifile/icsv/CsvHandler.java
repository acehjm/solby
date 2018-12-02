package me.solby.ifile.icsv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-11-29
 */
public class CsvHandler {
    private static final Logger logger = LoggerFactory.getLogger(CsvHandler.class);
    private static final String DEFAULT_SPLITER = ",";

    public CsvHandler(){}

    public static <T> void writeCsvFile(String csvName, List<T> list, LinkedHashMap<String, String> fieldMap,
                                        HttpServletResponse response) {
        try {
            response.reset();
            response.setContentType("application/octet-stream");
            csvName = URLEncoder.encode(csvName, "utf8");
            response.setHeader("Content-disposition", "attachment;filename="+csvName+".csv");
            response.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }

        try {
            OutputStream os = response.getOutputStream();
            CSVWriter writer = new CSVWriter(new OutputStreamWriter(os, "utf-8"), ',', '"', '"', "\n");
            fillCsv(writer, list, fieldMap);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static List<String[]> readCsvFile(File file, int startRow) {
        ArrayList<String[]> arrayList = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] nextLine;
            for (int i = 1; (nextLine = reader.readNext()) != null; ++i) {
                if (i >= startRow) {
                    arrayList.add(nextLine);
                }
            }
        } catch (IOException e) {
            logger.error("read file error", e);
        }
        return arrayList;
    }

    private static Field getFieldByName(String fieldName, Class<?> clazz) {
        Field[] selfFields = clazz.getDeclaredFields();
        for (Field field : selfFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null && superClazz != Object.class) {
            return getFieldByName(fieldName, superClazz);
        } else {
            return null;
        }
    }

    private static Object getFieldValueByName(String fieldName, Object obj) throws IllegalAccessException {
        Field field = getFieldByName(fieldName, obj.getClass());
        if (field != null) {
            field.setAccessible(true);
            return field.get(obj);
        } else {
            throw new RuntimeException(obj.getClass().getSimpleName() + "field " + fieldName +
                    "not exits in class");
        }
    }

    private static <T> void fillCsv(CSVWriter writer, List<T> list, LinkedHashMap<String, String> fieldMap) throws IllegalAccessException {
        String[] enFields = new String[fieldMap.size()];
        String[] cnFields = new String[fieldMap.size()];
        int count = 0;
        for (Map.Entry<String, String> stringEntry : fieldMap.entrySet()) {
            enFields[count] = stringEntry.getKey();
            cnFields[count] = stringEntry.getValue();
        }

        String[] includeHeaders = new String[count];
        System.arraycopy(cnFields, 0, includeHeaders, 0, enFields.length);

        writer.writeNext(includeHeaders);
        String[] data = new String[count];
        for (T item : list) {
            for (int j = 0; j < enFields.length; j++) {
                Object objValue = getFieldValueByName(enFields[j], item);
                String fieldValue = objValue == null ? "" : "\t" + objValue.toString();
                data[j] = fieldValue;
            }
            writer.writeNext(data);
        }
    }

}
