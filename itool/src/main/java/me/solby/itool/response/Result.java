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

    private String code;
    private String msg;
    private T data;

    public boolean isSuccess() {
        return "0".equals(code);
    }

}
