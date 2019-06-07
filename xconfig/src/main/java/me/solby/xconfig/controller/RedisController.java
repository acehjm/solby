package me.solby.xconfig.controller;

import me.solby.itool.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * me.solby.xconfig.controller
 *
 * @author majhdk
 * @date 2019-06-05
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Cacheable(value = "users", key = "#userId")
    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable String userId) {
        return new User(userId, "1209301133");
    }

    @GetMapping("/users")
    public User getUserV2() {
        String ss = redisTemplate.boundValueOps("aa").get();
        if (null == ss) {
            User user = new User("001", "1209301133");
            redisTemplate.boundValueOps("aa").set(JsonUtil.toJson(user));
            return user;
        } else {
            System.out.println("from redis ");
            return JsonUtil.fromJson(ss, User.class);
        }
    }

}
