package me.solby.xtool.exception;

/**
 * me.solby.itool.exception
 *
 * @author majhdk
 * @date 2019-06-11
 */
public class HttpClientException extends RuntimeException {

    public HttpClientException(String message) {
        super(message);
    }

    public HttpClientException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
