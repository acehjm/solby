package me.solby.xboot.controller;

import me.solby.xboot.config.exception.BusinessException;
import me.solby.xboot.manage.I18NUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * me.solby.xboot.controller
 *
 * @author majhdk
 * @date 2019/8/27
 */
@RestController
@RequestMapping("/i18n")
public class I18NController {

    @GetMapping("/en_US")
    public void testWithEnUS() {
        Locale.setDefault(Locale.US);
        throw new BusinessException("10001");
    }

    @GetMapping("/zh_CN")
    public void testWithZhCN() {
        throw new BusinessException("10002", new Object[]{"0x0f10002"});
    }

    @GetMapping("/notexist")
    public String testWithMessages() {
        return I18NUtil.getMessage("notexist");
    }

    @GetMapping("/message")
    public String testWithNotExists() {
        Locale.setDefault(Locale.US);
        throw new BusinessException("10003", "0x0f10003 not exist");
    }

    @GetMapping("/format")
    public String testWithFormat() {
        throw new BusinessException("10004", "{0} not exist", new Object[]{"0x0f10004"});
    }

}
