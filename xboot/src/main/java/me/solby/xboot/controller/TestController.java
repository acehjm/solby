package me.solby.xboot.controller;

import me.solby.xtool.response.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/success")
    public Result<String> test() {
        return new Result<>("test");
    }

    @PreAuthorize("hasAuthority('ADMIN_00')")
    @GetMapping("/failure")
    public Result<String> noauth() {
        return new Result<>("noauth");
    }

}
