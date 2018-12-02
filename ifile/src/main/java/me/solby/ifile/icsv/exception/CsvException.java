package me.solby.ifile.icsv.exception;

/**
 * @author majhdk
 * @DESCRIPTION Csv自定义异常类
 * @date 2018-12-03
 */
public class CsvException extends RuntimeException {

    public CsvException(String message) {
        super(message);
    }

    public CsvException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
