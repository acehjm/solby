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

    public BusinessException(String message) {
        super(message);
        this.message = message;
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

}
