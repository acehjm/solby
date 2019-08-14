package me.solby.xtool.base;

import me.solby.xtool.exception.VerifyException;

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

}
