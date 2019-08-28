package me.solby.xconfig.manage;

import me.solby.xtool.response.Result;

/**
 * 响应工具类
 *
 * @author majhdk
 * @date 2019/8/27
 */
public class ResponseHolder {

    /**
     * 多语言Key格式化字符串
     */
    private static final String I18N_CODE_FORMAT = "error.%s.message";
    /**
     * 异常码前缀
     */
    private static String CODE_PREFIX = "";

    private ResponseHolder() {
    }

    /**
     * 生成完整的错误码
     *
     * @param code
     * @return
     */
    public static String codeWithFormat(String code) {
        if (Result.OK.equals(code)) {
            return code;
        }
        return CODE_PREFIX + code;
    }

    /**
     * 获取国际化消息
     *
     * @param code 错误码
     * @param args 消息参数
     * @return
     */
    public static String i18nWithFormat(String code, Object[] args) {
        String key = String.format(I18N_CODE_FORMAT, codeWithFormat(code));
        return I18NUtil.getMessage(key, args);
    }

    /**
     * 设置错误码前缀
     *
     * @param prefix
     */
    public static void setCodePrefix(String prefix) {
        ResponseHolder.CODE_PREFIX = prefix;
    }

}
