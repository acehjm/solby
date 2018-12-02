package me.solby.ifile.icsv.validate;

import me.solby.ifile.icsv.constant.ValidFailType;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-12-01
 */
public class DataReadRecord<T> {

    private String progressCode;
    private int totalLine = 0;
    private int runStatus = 1;
    private long finishTime = Calendar.getInstance().getTimeInMillis();
    private File readFile;
    private List<String> heads = new ArrayList<>();
    private List<DataValidResult<T>> results = new LinkedList<>();
    private List<DataValidResult<T>> fails = new LinkedList<>();
    private List<DataValidResult<T>> resultAll = new ArrayList<>();

    public DataReadRecord(String progressCode, int totalLine) {
        this.progressCode = progressCode;
        this.totalLine = totalLine;
    }

    public void appendHead(String str) {
        this.heads.add(str);
    }

    public List<String> getHeads() {
        return this.heads;
    }

    public int getTotalLine() {
        return this.totalLine;
    }

    public void setTotalLine(int totalLine) {
        this.totalLine = totalLine;
    }

    public void addResult(DataValidResult<T> result) {
        this.resultAll.add(result);
        if (result.isValid()) {
            this.results.add(result);
        } else {
            this.fails.add(result);
        }
    }

    public void changeResult(DataValidResult<T> result) {
        if (!result.isValid()) {
            this.results.remove(result);
            this.fails.add(result);
        } else {
            this.fails.remove(result);
            this.results.add(result);
        }
    }

    public String getProgressCode() {
        return this.progressCode;
    }

    public double progress() {
        return this.totalLine == 0 ? 1.0D : (double) this.resultAll.size() / (double) this.totalLine;
    }

    public List<DataValidResult<T>> getResults() {
        return this.results;
    }

    public List<T> getValidEntities() {
        List<T> list = new ArrayList<>();
        this.results.forEach(result -> list.add(result.getEntity()));
        return list;
    }

    public List<DataValidResult<T>> getSuccessfulResults(ValidFailType... failTypes) {
        if (failTypes == null || failTypes.length == 0) return this.results;

        List<DataValidResult<T>> list = new ArrayList<>();

        for (DataValidResult<T> result : this.results) {
            List<DataValidMetaResult> metaResults = result.getDataValidMetaResults();
            boolean match = false;
            List<ValidFailType> validFailTypeList = new ArrayList<>();

            for (DataValidMetaResult metaResult : metaResults) {
                validFailTypeList.add(metaResult.getValidFailType());
            }
            for (ValidFailType failType : failTypes) {
                if (validFailTypeList.contains(failType)) {
                    match = true;
                    break;
                }
            }
            if (match) {
                list.add(result);
            }
        }
        return list;
    }

    public List<DataValidResult<T>> getFailedResults(ValidFailType... failTypes) {
        if (failTypes == null || failTypes.length == 0) return this.results;

        List<DataValidResult<T>> list = new ArrayList<>();
        for (DataValidResult<T> fail : fails) {
            boolean match = false;

            for (ValidFailType failType : failTypes) {
                if (fail.getValidFailType() == failType) {
                    match = true;
                    break;
                }
            }
            if (match) {
                list.add(fail);
            }
        }
        return list;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public long getFinishTime() {
        return this.finishTime;
    }

    public int getRunStatus() {
        return this.runStatus;
    }

    public void setRunStatus(int runStatus) {
        this.runStatus = runStatus;
    }

    public File getReadFile() {
        return this.readFile;
    }

    public void setReadFile(File readFile) {
        this.readFile = readFile;
    }
}
