package me.solby.xconfig.config;

import me.solby.xtool.json.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * me.solby.xconfig.config
 *
 * @author majhdk
 * @date 2019-06-30
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class)})
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisConfigTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void getUserV2Test() {
        String ss = redisTemplate.boundValueOps("aa").get();
        if (null == ss) {
            User user = new User("001", "1209301133");
            redisTemplate.boundValueOps("aa").set(JsonUtil.toJson(user));
            System.out.println(user);
        } else {
            System.out.println("from redis ");
            System.out.println(JsonUtil.fromJson(ss, User.class));
        }
    }

}