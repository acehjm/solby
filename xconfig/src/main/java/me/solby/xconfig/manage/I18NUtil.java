package me.solby.xconfig.manage;

import java.util.Locale;

/**
 * me.solby.xconfig.manage
 *
 * @author majhdk
 * @date 2019-08-26
 */
public class I18NUtil {

    /**
     * 获取国际化参数
     *
     * @param code 消息KEY
     * @return
     */
    private String getMessage(String code) {
        return this.getMessage(code, null);
    }

    /**
     * 获取国际化消息
     *
     * @param code 消息KEY
     * @param args 消息参数
     * @return
     */
    private String getMessage(String code, Object[] args) {
        return this.getMessage(code, args, null);
    }

    /**
     * 获取国际化消息
     *
     * @param code   消息KEY
     * @param args   消息参数
     * @param locale 语言
     * @return
     */
    private String getMessage(String code, Object[] args, Locale locale) {
        return AppContext.getApplicationContext().getMessage(code, args, locale);
    }

}