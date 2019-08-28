package me.solby.xconfig.config.exception;

import lombok.Getter;

/**
 * me.solby.itool.exception
 *
 * @author majhdk
 * @date 2019-07-16
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private String code;
    /**
     * 错误消息
     */
    private String message;
    /**
     * 错误消息参数
     */
    private Object[] args;

    public BusinessException(String code) {
        this.code = code;
    }

    public BusinessException(String code, Object[] args) {
        this.code = code;
        this.args = args;
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String code, String message, Object[] args) {
        super(message);
        this.code = code;
        this.message = message;
        this.args = args;
    }

    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }

    public BusinessException(final BaseError error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public BusinessException(final BaseError error, Object[] args) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = error.getMessage();
        this.args = args;
    }

}
