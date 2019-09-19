package me.solby.xboot.controller;

import me.solby.xboot.controller.vo.JsonDemoVO;
import me.solby.xboot.config.exception.BaseError;
import me.solby.xboot.config.exception.BusinessException;
import me.solby.xboot.domain.repository.redis.RedisCacheRepository;
import me.solby.xtool.json.JsonUtil;
import me.solby.xtool.response.Result;
import me.solby.xtool.verify.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * me.solby.xboot.controller
 *
 * @author majhdk
 * @date 2019-07-10
 */
@RestController
@RequestMapping("/json")
public class JsonController {

    @Autowired
    private RedisCacheRepository redisCacheRepository;

    @GetMapping
    public Result<JsonDemoVO> getJson() {
        var jsonDemoVO = new JsonDemoVO();
        jsonDemoVO.setItem("aaa");
        jsonDemoVO.setName("bbb");
        jsonDemoVO.setParent(null);
        jsonDemoVO.setCreatedTime(Instant.now());
        jsonDemoVO.setToday(LocalDate.now());
        jsonDemoVO.setNow(LocalDateTime.now());
        return new Result<>(jsonDemoVO);
    }

    @Cacheable(cacheNames = "kk", key = "#key")
    @GetMapping("/cache")
    public String getCache(@RequestParam String key) {
        return "cached by: " + key;
    }

    @GetMapping("/cache/custom")
    public String getCacheByCustom(@RequestParam String key) {
        String ss = redisCacheRepository.get("kk::" + key);
        if (ObjectUtil.isEmpty(ss)) {
            redisCacheRepository.set("kk::" + key, "from redis cached");
            return "not cached from redis";
        } else {
            return ss;
        }
    }

    @GetMapping("/notfound/{aa}")
    public Result<JsonDemoVO> notfound(@PathVariable String aa, @RequestParam String bb) {
        throw new BusinessException(aa + bb);
    }

    @PostMapping
    public Result postJson(@RequestBody JsonDemoVO demoVO) {
        System.out.println(JsonUtil.toJson(demoVO));
        if (demoVO.getItem().equals("aa")) {
            throw new BusinessException("xxxxx");
        }
        throw new BusinessException(new BaseError() {
            @Override
            public String getMessage() {
                return "failure";
            }

            @Override
            public String getCode() {
                return "198";
            }
        });
    }

}
