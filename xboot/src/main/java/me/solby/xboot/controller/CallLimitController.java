package me.solby.xboot.controller;

import me.solby.xboot.config.annotation.RequestLimit;
import me.solby.xboot.config.annotation.enums.LimitTypeEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * me.solby.xboot.controller
 *
 * @author majhdk
 * @date 2019-08-11
 */
@RestController
@RequestMapping("/call")
public class CallLimitController {

    @RequestLimit(type = LimitTypeEnum.INTERFACE, count = 10, time = 30)
    @GetMapping("/limit01")
    public String call01() {
        return "limit 01";
    }

    @RequestLimit(type = LimitTypeEnum.ACCOUNT_INTERFACE, count = 10, time = 30)
    @GetMapping("/limit02")
    public String call02() {
        return "limit 02";
    }

}
