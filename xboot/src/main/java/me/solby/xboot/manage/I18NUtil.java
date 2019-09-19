package me.solby.xboot.manage;

import java.util.Locale;

/**
 * 国际化工具类
 *
 * @author majhdk
 * @date 2019-08-26
 */
public class I18NUtil {

    private I18NUtil() {
    }

    /**
     * 获取国际化消息
     *
     * @param key 消息KEY
     * @return
     */
    public static String getMessage(String key) {
        return getMessage(key, null, Locale.getDefault());
    }

    /**
     * 获取国际化消息
     *
     * @param key  消息KEY
     * @param args 消息参数
     * @return
     */
    public static String getMessage(String key, Object[] args) {
        return getMessage(key, args, Locale.getDefault());
    }

    /**
     * 获取国际化消息
     *
     * @param key    消息KEY
     * @param locale 语言
     * @return
     */
    public static String getMessage(String key, Locale locale) {
        return getMessage(key, null, locale);
    }

    /**
     * 获取国际化消息
     *
     * @param key    消息KEY
     * @param args   消息参数
     * @param locale 语言
     * @return
     */
    private static String getMessage(String key, Object[] args, Locale locale) {
        // if not found, the default message is key
        return AppContext.getApplicationContext().getMessage(key, args, key, locale);
    }

}
