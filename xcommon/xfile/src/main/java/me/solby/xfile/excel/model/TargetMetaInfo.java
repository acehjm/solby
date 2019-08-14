package me.solby.xfile.excel.model;

import lombok.Data;

/**
 * @author majhdk
 * @DESCRIPTION Excel数据类注解信息类
 * @date 2018-12-01
 */
@Data
public class TargetMetaInfo {

    /**
     * 列名
     */
    private String column;
    /**
     * 属性名称
     */
    private String fieldName;
    /**
     * Excel中列位置
     */
    private int columnPosition;

    /**
     * 属性类型
     * TODO 待配置
     */
    private Class<?> tClass;

    public TargetMetaInfo() {
    }

    public TargetMetaInfo(String column, String fieldName, Class<?> tClass) {
        this.column = column;
        this.fieldName = fieldName;
        this.tClass = tClass;
    }
}
