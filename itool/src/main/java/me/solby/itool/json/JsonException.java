package me.solby.itool.json;

/**
 * @author majhdk
 * @DESCRIPTION 自定义Json异常处理类
 * @date 2018-12-02
 */
public class JsonException extends RuntimeException {

    JsonException(String message) {
        super(message);
    }

    JsonException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
