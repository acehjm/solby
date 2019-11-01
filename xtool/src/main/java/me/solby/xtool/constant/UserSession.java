package me.solby.xtool.constant;

import lombok.Data;

/**
 * me.solby.xtool.constant
 *
 * @author majhdk
 * @date 2019-08-26
 */
@Data
public class UserSession {

    /**
     * 用户名
     */
    private String username;
    /**
     * 用户ID
     */
    private String userid;
    /**
     * 客户端IP
     */
    private String clientIP;
    /**
     * 客户端MAC
     */
    private String clientMAC;
    /**
     * 语言
     */
    private String language;

}
