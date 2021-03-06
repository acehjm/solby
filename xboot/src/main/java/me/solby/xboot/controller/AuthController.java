package me.solby.xboot.controller;

import me.solby.xboot.domain.entity.UserDO;
import me.solby.xboot.domain.repository.UserRepository;
import me.solby.xoauth.jwt.JwtTokenUtil;
import me.solby.xoauth.jwt.JwtUser;
import me.solby.xtool.json.JsonUtil;
import me.solby.xtool.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public Result<String> login(@RequestBody UserDO userDO) {

        return new Result<>("success");
    }

    @GetMapping("/token")
    public Result<String> token(@RequestParam String username) {
        UserDO userDO = userRepository.findByUserName(username);

        JwtUser jwtUser = new JwtUser(userDO.getUserName(), userDO.getUserId(), List.of("ADMIN", "USER", "GUEST"));
        String token = JwtTokenUtil.token(JsonUtil.toJson(jwtUser));

        JwtUser info = JwtTokenUtil.parseToken(token, JwtUser.class);
        System.out.println("Parse token info: " + JsonUtil.toJson(info));
        return new Result<>(token);
    }

    @DeleteMapping("/userid")
    public void delete(@RequestParam String userId) {
        System.out.println("delete success >> " + userId);
    }

}
