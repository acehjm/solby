package me.solby.ifile.icsv.validate;

import me.solby.ifile.icsv.constant.ValidFailType;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-12-01
 */
public class DataValidMetaResult {

    private ValidFailType validFailType;
    private String reason;
    private String[] reasonParams;

    public DataValidMetaResult(ValidFailType validFailType, String reason) {
        this.validFailType = validFailType;
        this.reason = reason;
    }

    public DataValidMetaResult(ValidFailType validFailType, String reason, String[] reasonParams) {
        this.validFailType = validFailType;
        this.reason = reason;
        this.reasonParams = reasonParams;
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
}
