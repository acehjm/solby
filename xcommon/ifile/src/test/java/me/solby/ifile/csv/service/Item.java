package me.solby.ifile.csv.service;

import com.opencsv.bean.CsvBindByName;
import me.solby.ifile.csv.annotation.CsvFileDesc;

/**
 * me.solby.ifile.icsv.service
 *
 * @author majhdk
 * @date 2019-08-04
 */
@CsvFileDesc(skipLines = 1)
public class Item {
    @CsvBindByName(column = "名称")
    private String name;
    @CsvBindByName(column = "大小")
    private Integer size;
    @CsvBindByName(column = "颜色")
    private String color;

    private String invStr;

    public Item(){}

    public Item(String name, Integer size, String color) {
        this.name = name;
        this.size = size;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getInvStr() {
        return invStr;
    }

    public void setInvStr(String invStr) {
        this.invStr = invStr;
    }
}
