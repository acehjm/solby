package me.solby.ifile.iexcel;

/**
 * @author majhdk
 * @DESCRIPTION Excel数据类注解信息类
 * @date 2018-12-01
 */
public class ExcelMetaInfo {

    private String title;  //表头名称
    private int order;  //表头序号
    private String fieldName;  //属性名称

    public ExcelMetaInfo(String title, int order, String fieldName) {
        this.title = title;
        this.order = order;
        this.fieldName = fieldName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
