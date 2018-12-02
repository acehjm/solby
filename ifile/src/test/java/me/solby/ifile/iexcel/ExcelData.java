package me.solby.ifile.iexcel;

import me.solby.ifile.iexcel.annotation.ExcelColumnDesc;
import me.solby.ifile.iexcel.annotation.ExcelFileDesc;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-12-02
 */
@ExcelFileDesc(charset = "GB2312", ignoreRows = 1)
public class ExcelData {

    @ExcelColumnDesc(title = "名称", order = 1)
    private String name;

    @ExcelColumnDesc(title = "编号", order = 2)
    private String index;

    public ExcelData(){}

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
