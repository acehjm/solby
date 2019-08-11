package me.solby.ifile.excel.exception;

/**
 * @author majhdk
 * @DESCRIPTION 自定义异常类
 * @date 2018-12-02
 */
public class ExcelException extends RuntimeException {

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }

}
