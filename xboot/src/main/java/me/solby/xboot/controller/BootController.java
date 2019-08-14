package me.solby.xboot.controller;

import me.solby.xtool.response.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * me.solby.xboot.controller
 *
 * @author majhdk
 * @date 2019-07-26
 */
@RestController
public class BootController {

    @GetMapping("/runner")
    public Result<String> running() {
        System.out.println("开始运行");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("运行结束");
        return new Result<>();
    }

}
