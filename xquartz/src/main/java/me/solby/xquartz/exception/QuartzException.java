package me.solby.xquartz.exception;

/**
 * me.solby.xquartz.acture
 *
 * @author majhdk
 * @date 2019-06-22
 */
public class QuartzException extends RuntimeException {

    public QuartzException(String message) {
        super(message);
    }

    public QuartzException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
