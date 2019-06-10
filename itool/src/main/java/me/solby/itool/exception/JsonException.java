package me.solby.itool.exception;

/**
 * @author majhdk
 * @DESCRIPTION 自定义Json异常处理类
 * @date 2018-12-02
 */
public class JsonException extends RuntimeException {

    public JsonException(String message) {
        super(message);
    }

    public JsonException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
