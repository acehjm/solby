package me.solby.itool.response;

import lombok.Data;

/**
 * 通用返回结果封装
 *
 * @author majhdk
 * @date 2019-06-14
 */
@Data
public class Result<T> {

    public static final String OK = "0";
    public static final String MSG_SUCCESS = "success";

    private String code;
    private String msg;
    private T data;

    public Result() {
        this.code = OK;
        this.msg = MSG_SUCCESS;
    }

    public Result(T data) {
        this.code = OK;
        this.msg = MSG_SUCCESS;
        this.data = data;
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result failure(String code, String msg) {
        return new Result(code, msg);
    }

    public static Result success() {
        return new Result();
    }

}
