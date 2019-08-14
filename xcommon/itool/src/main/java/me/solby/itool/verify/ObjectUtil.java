package me.solby.itool.verify;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-12-23
 */
public class ObjectUtil {

    private ObjectUtil() {
    }

    /*
     * 空检查
     */

    /**
     * 判断对象为空
     *
     * @param object
     * @return
     */
    public static boolean isEmpty(final Object object) {
        if (null == object) {
            return true;
        }
        if (object instanceof CharSequence) {
            return ((CharSequence) object).length() == 0;
        }
        if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        }
        if (object instanceof Collection<?>) {
            return ((Collection<?>) object).isEmpty();
        }
        if (object instanceof Map<?, ?>) {
            return ((Map<?, ?>) object).isEmpty();
        }
        return false;
    }

    /**
     * 判断对象不为空
     *
     * @param object
     * @return
     */
    public static boolean isNotEmpty(final Object object) {
        return !isEmpty(object);
    }

    /*
     * 默认值
     */

    /**
     * 对象为空，则返回默认值
     *
     * @param object
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T defaultIfEmpty(final T object, final T defaultValue) {
        return isEmpty(object) ? defaultValue : object;
    }

    /*
     * 其它
     */

    /**
     * 比较两个对象，空安全
     *
     * @param c1
     * @param c2
     * @param <T>
     * @return
     */
    public static <T extends Comparable<? super T>> int compare(final T c1, final T c2) {
        return compare(c1, c2, false);
    }

    /**
     * 比较两个对象，空安全
     *
     * @param c1
     * @param c2
     * @param nullGreater
     * @param <T>
     * @return
     */
    public static <T extends Comparable<? super T>> int compare(final T c1, final T c2, final boolean nullGreater) {
        if (c1 == c2) {
            return 0;
        } else if (c1 == null) {
            return nullGreater ? 1 : -1;
        } else if (c2 == null) {
            return nullGreater ? -1 : 1;
        }
        return c1.compareTo(c2);
    }

}
