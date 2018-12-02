package me.solby.ifile.icsv.validate;

import me.solby.ifile.icsv.constant.ValidFailType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-12-01
 */
public class DataValidResult<T> {

    private int rowNo;
    private boolean valid;
    private String lineStr;
    private T entity;
    private ValidFailType validFailType;
    private String reason;
    private String[] reasonParams;
    private List<DataValidMetaResult> dataValidMetaResults = new ArrayList<>();

    public DataValidResult(int rowNo, String lineStr, T entity) {
        this.rowNo = rowNo;
        this.lineStr = lineStr;
        this.entity = entity;
    }

    public DataValidResult(int rowNo, String lineStr, ValidFailType type, String reason) {
        this.rowNo = rowNo;
        this.lineStr = lineStr;
        this.validFailType = type;
        this.reason = reason;
    }

    public void success() {
        this.valid = true;
    }

    public void fail(ValidFailType type, String reason) {
        this.valid = false;
        this.reason = reason;
        this.validFailType = type;
        this.dataValidMetaResults.add(new DataValidMetaResult(type, reason));
    }

    public void fail(ValidFailType type, String reason, String[] reasonParams) {
        this.validFailType = type;
        this.reason = reason;
        this.reasonParams = reasonParams;
        this.dataValidMetaResults.add(new DataValidMetaResult(type, reason, reasonParams));
    }

    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getLineStr() {
        return lineStr;
    }

    public void setLineStr(String lineStr) {
        this.lineStr = lineStr;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public ValidFailType getValidFailType() {
        return validFailType;
    }

    public void setValidFailType(ValidFailType validFailType) {
        this.validFailType = validFailType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String[] getReasonParams() {
        return reasonParams;
    }

    public void setReasonParams(String[] reasonParams) {
        this.reasonParams = reasonParams;
    }

    public List<DataValidMetaResult> getDataValidMetaResults() {
        return dataValidMetaResults;
    }

    public void setDataValidMetaResults(List<DataValidMetaResult> dataValidMetaResults) {
        this.dataValidMetaResults = dataValidMetaResults;
    }
}
