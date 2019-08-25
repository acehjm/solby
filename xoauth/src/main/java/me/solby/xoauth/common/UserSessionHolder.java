package me.solby.xoauth.common;

import lombok.Data;
import me.solby.xtool.constant.UserSession;

/**
 * 用户全局信息
 *
 * @author majhdk
 * @date 2019-08-15
 */
@Data
public class UserSessionHolder {

    /**
     * 已授权用户缓存
     */
    public static final ThreadLocal<UserSession> userSessionThreadLocal = new ThreadLocal<>();

}
