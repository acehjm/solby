package me.solby.xoauth.exception;

/**
 * me.solby.xoauth.exception
 *
 * @author majhdk
 * @date 2019-05-26
 */
public class JwtTokenException extends RuntimeException {

    public JwtTokenException(String message) {
        super(message);
    }

    public JwtTokenException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
