package me.solby.xtool.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * me.solby.xtool.base
 *
 * @author majhdk
 * @date 2019/11/2
 */
public class StreamUtil {
    private StreamUtil(){}

    /**
     * stream 中对象去重
     *
     * @param keyExtractor 表达式
     * @param <T> 范型
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
