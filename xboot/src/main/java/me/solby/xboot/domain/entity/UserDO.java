package me.solby.xboot.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserDO {

    private String userId;

    private String userName;

    private String password;

    private String phoneNum;

    private List<String> authorities;

    public UserDO(String userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

}
