package me.solby.xoauth.common;

import lombok.Data;
import me.solby.xoauth.jwt.JwtUser;

/**
 * 用户全局信息
 *
 * @author majhdk
 * @date 2019-08-15
 */
@Data
public class UserHolder {

    /**
     * 已授权用户缓存
     */
    public static ThreadLocal<JwtUser> userThreadLocal = new ThreadLocal<>();

}
