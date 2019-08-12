package me.solby.xboot.controller;

import me.solby.xconfig.annotation.RequestLimit;
import me.solby.xconfig.annotation.enums.LimitTypeEnum;
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

}
