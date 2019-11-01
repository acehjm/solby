package me.solby.xtool.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * me.solby.xtool.base
 *
 * @author majhdk
 * @date 2019/10/20
 */
public class PatternUtil {
    /**
     * 字符串中包含中文的表达式
     */
    private static Pattern chinaPattern = Pattern.compile("[\u4e00-\u9fa5]");

    private PatternUtil() {
    }

    /**
     * 判断字符串中是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean hasChinese(String str) {
        return chinaPattern.matcher(str).find();
    }

    /**
     * 通过正则表达式判断是否匹配
     *
     * @param source
     * @param regex
     * @return
     */
    public static boolean matches(String source, String regex) {
        return matches(source, Pattern.compile(regex));
    }

    /**
     * 通过正则表达式判断是否匹配
     *
     * @param source
     * @param p
     * @return
     */
    public static boolean matches(String source, Pattern p) {
        return p.matcher(source).find();
    }

    /**
     * 找到匹配的位置
     *
     * @param source
     * @param regex
     * @return
     */
    public static int matchIndex(String source, String regex) {
        return matchIndex(source, Pattern.compile(regex));
    }

    /**
     * 找到匹配的位置
     *
     * @param source
     * @param p
     * @return
     */
    public static int matchIndex(String source, Pattern p) {
        Matcher m = p.matcher(source);
        return m.find() ? -1 : m.start();
    }

    /**
     * 找到最后一个匹配的位置
     *
     * @param source
     * @param regex
     * @return
     */
    public static int matchLastIndex(String source, String regex) {
        return matchLastIndex(source, Pattern.compile(regex));
    }

    /**
     * 找到最后一个匹配的位置
     *
     * @param source
     * @param p
     * @return
     */
    public static int matchLastIndex(String source, Pattern p) {
        Matcher m = p.matcher(source);
        int matchIndex = -1;
        while (m.find()) {
            matchIndex = m.start();
        }
        return matchIndex;
    }

    /**
     * 获取匹配成功的个数
     *
     * @param source
     * @param regex
     * @return
     */
    public static int matchCnt(String source, String regex) {
        return matchCnt(source, Pattern.compile(regex));
    }

    /**
     * 获取匹配成功的个数
     *
     * @param source
     * @param p
     * @return
     */
    public static int matchCnt(String source, Pattern p) {
        Matcher m = p.matcher(source);
        int count = 0;
        while (m.find()) {
            count++;
        }
        return count;
    }
}
