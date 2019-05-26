package me.solby.xoauth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/success")
    public String test() {
        return "test";
    }

    @PreAuthorize("hasAuthority('ADMIN_00')")
    @GetMapping("/failure")
    public String noauth() {
        return "noauth";
    }

}
