package me.solby.xoauth.jwt;

import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * 登录时，根据该类生成JWT Token
 * - 过期时间配置为当前时间加过期天数
 *
 * @author majhdk
 * @date 2019-05-26
 */
@Data
public class JwtUser {

    private Long expired;
    private String username;
    private String userid;
    private List<String> authorities;

    public JwtUser() {
    }

    public JwtUser(String username, String userid, List<String> authorities) {
        this.username = username;
        this.userid = userid;
        this.authorities = authorities;
        this.expired = expired();
    }

    private Long expired() {
        return Instant.now().toEpochMilli() + 3 * 24 * 60 * 60 * 1000L;
    }
}
