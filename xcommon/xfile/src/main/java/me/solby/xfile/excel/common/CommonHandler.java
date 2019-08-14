package me.solby.xfile.excel.common;

import me.solby.xfile.excel.annotation.ExcelColumnDesc;
import me.solby.xfile.excel.model.TargetMetaInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author majhdk
 * @DESCRIPTION Excel通用处理类
 * @date 2018-12-02
 */
public final class CommonHandler {

    private CommonHandler() {
    }

    /**
     * 获取Excel数据类注解信息
     *
     * @param clz Excel数据类
     * @return
     */
    public static List<TargetMetaInfo> getExcelMetaList(Class clz) {
        List<TargetMetaInfo> headers = new ArrayList<>();
        Field[] fields = clz.getDeclaredFields();  //获取所有已申明属性
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelColumnDesc.class)) {  //是否被ExcelColumnDesc注解
                ExcelColumnDesc column = field.getAnnotation(ExcelColumnDesc.class); //获取注解类
                headers.add(new TargetMetaInfo(column.column(), field.getName(), field.getType())); //获取元信息
            }
        }
        return headers;
    }

}
