package me.solby.itool.verify;

import me.solby.itool.exception.VerifyException;

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
    public static void checkTrue(boolean result, String message) {
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
    public static void checkFalse(boolean result, String message) {
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
    public static void checkEquals(Object obj1, Object obj2, String message) {
        if (!obj1.equals(obj2)) {
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
    public static void checkNotEquals(Object obj1, Object obj2, String message) {
        if (obj1.equals(obj2)) {
            throw new VerifyException(message);
        }
    }

    /**
     * 校验为空
     *
     * @param obj
     * @param message
     */
    public static void checkEmpty(Object obj, String message) {
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
    public static void checkNotEmpty(Object obj, String message) {
        if (ObjectUtil.isEmpty(obj)) {
            throw new VerifyException(message);
        }
    }

}
