package me.solby.ifile.iexcel;

import lombok.Data;

/**
 * @author majhdk
 * @DESCRIPTION Excel数据类注解信息类
 * @date 2018-12-01
 */
@Data
public class ExcelMetaInfo {

    private String title;  //表头名称
    private int order;  //表头序号
    private String fieldName;  //属性名称

    public ExcelMetaInfo() {
    }

    public ExcelMetaInfo(String title, int order, String fieldName) {
        this.title = title;
        this.order = order;
        this.fieldName = fieldName;
    }
}
