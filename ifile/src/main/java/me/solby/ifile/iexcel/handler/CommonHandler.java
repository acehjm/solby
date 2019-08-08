package me.solby.ifile.iexcel.handler;

import me.solby.ifile.iexcel.ExcelMetaInfo;
import me.solby.ifile.iexcel.annotation.ExcelColumnDesc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author majhdk
 * @DESCRIPTION Excel通用处理类
 * @date 2018-12-02
 */
public final class CommonHandler {

    private CommonHandler(){
    }

    /**
     * Excel后缀--hssf-2003及以下版本
     */
    public static final String EXCEL_HSSF_SUFFIX = ".xls";
    /**
     * Excel后缀--hssf-2007及以上版本
     */
    public static final String EXCEL_XSSF_SUFFIX = ".xlsx";

    /**
     * 获取Excel数据类注解信息
     *
     * @param clz Excel数据类
     * @return
     */
    public static List<ExcelMetaInfo> getExcelMetaList(Class clz) {
        List<ExcelMetaInfo> headers = new ArrayList<>();
        Field[] fields = clz.getDeclaredFields();  //获取所有已申明属性
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelColumnDesc.class)) {  //是否被ExcelColumnDesc注解
                ExcelColumnDesc column = field.getAnnotation(ExcelColumnDesc.class); //获取注解类
                headers.add(new ExcelMetaInfo(column.title(), column.order(), field.getName())); //获取元信息
            }
        }
        Class superClz = clz.getSuperclass();  //递归获取父类Excel元数据
        if (null != superClz && superClz != Object.class) {
            return getExcelMetaList(superClz);
        }
        return headers;
    }

}
