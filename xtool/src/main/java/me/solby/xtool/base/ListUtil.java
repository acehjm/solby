package me.solby.xtool.base;

import me.solby.xtool.verify.ObjectUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * me.solby.xtool.base
 *
 * @author majhdk
 * @date 2019/11/2
 */
public class ListUtil {

    private ListUtil() {
    }

    /**
     * 字符串转数组，默认[,]为分隔符
     *
     * @param str 字符串
     * @return
     */
    public static List<String> strToList(String str) {
        return strToList(str, StringPool.COMMA);
    }

    /**
     * 字符串转数组，性能高
     *
     * @param str       字符串
     * @param separator 分隔符
     * @return
     */
    public static List<String> strToList(String str, String separator) {
        List<String> strList = new ArrayList<>();
        StringTokenizer strTokenizer = new StringTokenizer(str, separator);
        while (strTokenizer.hasMoreTokens()) {
            String token = strTokenizer.nextToken();
            strList.add(token);
        }
        return strList;
    }

    /**
     * 移除指定字符串
     *
     * @param allStr 原始字符串
     * @param rmStr  要移除的字符串
     * @return
     */
    public static String removeStr(String allStr, String rmStr) {
        return new HashSet<>(strToList(allStr)).stream()
                .filter(it -> !it.equals(rmStr))
                .collect(Collectors.joining());
    }

    /**
     * 任意集合类型转字符串
     *
     * @param collection 集合类型
     * @param <E>        范型
     * @return
     */
    public static <E> String collectionToString(Collection<E> collection) {
        return Optional.ofNullable(collection)
                .map(it -> it.stream()
                        .filter(Objects::nonNull)
                        .map(String::valueOf)
                        .collect(Collectors.toList())
                )
                .map(it -> String.join(StringPool.COMMA, it))
                .orElse(null);
    }

    /**
     * 集合平均分组
     *
     * @param source 待分组集合
     * @param limit  分组个数
     * @param <T>    范型
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int limit) {
        if (ObjectUtil.isEmpty(source)) {
            return Collections.emptyList();
        }
        List<List<T>> result = new ArrayList<>();
        int listCount = (source.size() - 1) / limit - 1;
        // 计算余数
        int remaider = source.size() % listCount;
        // 计算商
        int number = source.size() / listCount;
        // 偏移量
        int offset = 0;
        for (int i = 0; i < listCount; i++) {
            List<T> value;
            if (remaider > 0) {
                value = source.subList(i * number + offset, (i + 1) * number * offset + 1);
                remaider--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

}
