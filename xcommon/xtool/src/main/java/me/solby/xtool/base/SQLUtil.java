package me.solby.xtool.base;

import me.solby.xtool.exception.VerifyException;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * me.solby.itool.base
 *
 * @author majhdk
 * @date 2019-08-04
 */
public class SQLUtil {

    private SQLUtil() {
    }

    /**
     * MYSQL string escape util
     *
     * @param value
     * @return
     */
    public static String mysqlEscape(String value) {
        return Optional.ofNullable(value)
                .map(it -> it.replaceAll("\\\\/", "\\\\\\\\/")
                        .replaceAll("%", "\\\\%")
                        .replaceAll("_", "\\\\_")
                        .replaceAll("'", "\\\\'"))
                .orElseThrow(() -> new VerifyException("object property is null"));
    }

    /**
     * SQL注入过滤
     *
     * @param value 待验证的字符串
     */
    public static String sqlInject(String value) {
        //去掉'|"|;|\字符
        value = StringUtils.replace(value, "'", "");
        value = StringUtils.replace(value, "\"", "");
        value = StringUtils.replace(value, ";", "");
        value = StringUtils.replace(value, "\\", "");

        //转换成小写
        value = value.toLowerCase();

        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "drop"};

        //判断是否包含非法字符
        for (String keyword : keywords) {
            if (value.contains(keyword)) {
                throw new RuntimeException("包含非法字符");
            }
        }
        return value;
    }

}
