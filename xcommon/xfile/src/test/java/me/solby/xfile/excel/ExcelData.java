package me.solby.xfile.excel;

import me.solby.xfile.excel.annotation.ExcelColumnDesc;
import me.solby.xfile.excel.annotation.ExcelFileDesc;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-12-02
 */
@ExcelFileDesc(charset = "GB2312")
public class ExcelData {

    @ExcelColumnDesc(column = "名称")
    private String name;

    @ExcelColumnDesc(column = "编号")
    private String index;

    public ExcelData() {
    }

    public ExcelData(String name, String index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

}
