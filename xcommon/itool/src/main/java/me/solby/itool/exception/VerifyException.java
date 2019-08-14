package me.solby.itool.exception;

/**
 * 自定义验证异常
 *
 * @author majhdk
 * @date 2019-06-11
 */
public class VerifyException extends RuntimeException {

    public VerifyException(String message) {
        super(message);
    }

    public VerifyException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
