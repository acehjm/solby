package me.solby.ifile.icsv.serialize;

/**
 * @author majhdk
 * @DESCRIPTION 属性信息
 * @date 2018-11-29
 */
public class FieldInformation {

    private String fieldName;
    private Integer no;
    private boolean isIndex;

    public FieldInformation(){

    }

    public FieldInformation(String fieldName, Integer no, boolean isIndex){
        this.fieldName = fieldName;
        this.no = no;
        this.isIndex = isIndex;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Integer getNo() {
        return no;
    }

    public boolean isIndex() {
        return isIndex;
    }
}
