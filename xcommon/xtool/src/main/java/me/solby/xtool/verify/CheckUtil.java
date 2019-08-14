package me.solby.xtool.verify;

import me.solby.xtool.exception.VerifyException;

import java.util.Objects;

/**
 * 自定义校验工具类
 *
 * @author majhdk
 * @date 2019-06-11
 */
public class CheckUtil {

    private CheckUtil() {
    }

    /**
     * 校验结果为True
     *
     * @param result
     * @param message
     */
    public static void checkTrue(final boolean result, final String message) {
        if (!result) {
            throw new VerifyException(message);
        }
    }

    /**
     * 校验结果为False
     *
     * @param result
     * @param message
     */
    public static void checkFalse(final boolean result, final String message) {
        if (result) {
            throw new VerifyException(message);
        }
    }

    /**
     * 校验相同
     *
     * @param obj1
     * @param obj2
     * @param message
     */
    public static void checkEquals(final Object obj1, final Object obj2, final String message) {
        if (!Objects.equals(obj1, obj2)) {
            throw new VerifyException(message);
        }
    }

    /**
     * 校验不同
     *
     * @param obj1
     * @param obj2
     * @param message
     */
    public static void checkNotEquals(final Object obj1, final Object obj2, final String message) {
        if (Objects.equals(obj1, obj2)) {
            throw new VerifyException(message);
        }
    }

    /**
     * 校验为空
     *
     * @param obj
     * @param message
     */
    public static void checkEmpty(final Object obj, final String message) {
        if (ObjectUtil.isNotEmpty(obj)) {
            throw new VerifyException(message);
        }
    }

    /**
     * 校验不为空
     *
     * @param obj
     * @param message
     */
    public static void checkNotEmpty(final Object obj, final String message) {
        if (ObjectUtil.isEmpty(obj)) {
            throw new VerifyException(message);
        }
    }

}
