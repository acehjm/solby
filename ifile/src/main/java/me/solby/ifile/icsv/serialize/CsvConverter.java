package me.solby.ifile.icsv.serialize;

import com.fasterxml.jackson.core.type.TypeReference;
import me.solby.ifile.icsv.annotation.CsvColumnDesc;
import me.solby.ifile.json.JsonUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-11-29
 */
public class CsvConverter {

    public CsvConverter() {
    }

    public static <T> T toObject(String line, String spliter, TypeReference<T> type, List<FieldInformation> infos) {
        if (StringUtils.isBlank(line)) {
            return null;
        } else {
            String[] objStr = line.split(spliter);
            return toObject(objStr, type, infos);
        }
    }

    public static <T> T toObject(String[] lines, TypeReference<T> type, List<FieldInformation> infos) {
        if (lines != null && lines.length != 0 && infos != null && !infos.isEmpty()) {
            Map<String, String> map = new HashMap<>();
            for (FieldInformation info : infos) {
                if (info.getNo() < lines.length) {
                    map.put(info.getFieldName(), lines[info.getNo()].trim());
                }
            }
            return JsonUtil.toPojo(JsonUtil.toJson(map), type);
        }
        return null;
    }

    public static <T> List<FieldInformation> getFieldInformation(Class<T> tClass) {
        Field[] fields = tClass.getDeclaredFields();
        List<FieldInformation> infos = new ArrayList<>();
        for (Field field : fields) {
            CsvColumnDesc ccd = field.getAnnotation(CsvColumnDesc.class);
            if (null != ccd) {
                infos.add(new FieldInformation(field.getName(), ccd.no(), ccd.isIndex()));
            }
        }
        return infos;
    }

    public static String getIndexFieldName(List<FieldInformation> infos) {
        FieldInformation fi = null;
        Iterator iterator = infos.iterator();
        while (true) {
            FieldInformation info;
            do {
                if (!iterator.hasNext()) {
                    return fi == null ? null : fi.getFieldName();
                }
                info = (FieldInformation) iterator.next();
                if (info.isIndex()) {
                    return info.getFieldName();
                }
            } while (fi != null && fi.getNo() <= info.getNo());
            fi = info;
        }
    }

}
