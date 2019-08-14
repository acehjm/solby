package me.solby.xoauth.controller;

import me.solby.xtool.json.JsonUtil;
import me.solby.xoauth.domain.entity.UserDO;
import me.solby.xoauth.jwt.JwtTokenHelper;
import me.solby.xoauth.jwt.JwtUser;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 授权
 *
 * @author majhdk
 * @date 2019-05-25
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestBody UserDO userDO) {

        return "success";
    }

    @GetMapping("/token")
    public String token() {

        JwtUser jwtUser = new JwtUser("username", "001", List.of("ADMIN", "USER", "GUEST"));
        String token = JwtTokenHelper.token(JsonUtil.toJson(jwtUser));

        JwtUser info = JwtTokenHelper.getUserInfo(token, JwtUser.class);
        System.out.println("info " + JsonUtil.toJson(info));
        return token;
    }

    @DeleteMapping("/userid")
    public void delete(@RequestParam String userId) {
        System.out.println("delete success >> " + userId);
    }

}
