package me.solby.xconfig.config;

import me.solby.itool.json.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * me.solby.xconfig.config
 *
 * @author majhdk
 * @date 2019-06-30
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisConfigTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public User getUserV2Test() {
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