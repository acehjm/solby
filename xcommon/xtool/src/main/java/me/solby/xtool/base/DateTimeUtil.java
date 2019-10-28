package me.solby.xtool.base;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

/**
 * me.solby.xtool.base
 * TODO EDIT
 *
 * @author majhdk
 * @date 2019/10/19
 */
public class DateTimeUtil {
    /**
     * 英文月份名称
     */
    public static final String[] MONTH_ENGLISH_NAME = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};
    /**
     * 中文星期的名称
     */
    public static final String[] WEEK_CHINA_NAME = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
    /**
     * 英文日期的几种格式
     */
    public static final Collection<String> DEFAULT_PATTERNS = Arrays.asList("EEE MMM d HH:mm:ss yyyy",
            "EEE MMM dd HH:mm:ss Z yyyy", "EEE MMM dd Z yyyy", "EEE MMM dd yyyy", "EEEE, dd-MMM-yy HH:mm:ss zzz",
            "EEE, dd MMM yyyy HH:mm:ss zzz");
    private static final String[] CHINA_DATE_KEYS = {"○", "О", "0", "Ο", "O", "零", "一", "二", "三", "四", "五", "六", "七",
            "八", "九", "十", "年", "月", "日", "时", "分", "秒"};
    private static final String[] CHINA_DATE_KEY_MAP = {"0", "0", "0", "0", "0", "0", "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "10", "-", "-", " ", ":", ":", " "};

    private DateTimeUtil() {
    }

    /**
     * 将日期转化为中文格式
     *
     * @param dateTime
     * @return
     */
    public static String format2China(LocalDateTime dateTime) {
        if (null == dateTime) {
            return null;
        }
        GregorianCalendar pointDate = new GregorianCalendar();
        pointDate.set(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(),
                dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());

        StringBuilder result = new StringBuilder();
        result.append(pointDate.get(Calendar.YEAR)).append("年");
        result.append(pointDate.get(Calendar.MONTH) + 1).append("月");
        result.append(pointDate.get(Calendar.DAY_OF_MONTH)).append("日");
        result.append(pointDate.get(Calendar.HOUR_OF_DAY)).append("时");
        result.append(pointDate.get(Calendar.MINUTE)).append("分");
        result.append(pointDate.get(Calendar.SECOND)).append("秒");
        return result.toString();
    }

    /**
     * 定义日期的格式
     */
    public final static class FORMAT {
        public final static String YEAR_MONTH = "yyyy-MM";

        /**
         * 6位日期格式
         */
        public final static String DATE_6CHAR = "yyMMdd";

        /**
         * 8位日期格式
         */
        public final static String DATE_8CHAR = "yyyyMMdd";

        /**
         * 点号日期格式
         */
        public final static String DATE_DOT = "yyyy.MM.dd";

        /**
         * 反斜杠日期格式
         */
        public final static String DATE_SLASH = "yyyy/MM/dd";

        /**
         * 横杠日期格式
         */
        public final static String DATE_HORIZONTAL = "yyyy-MM-dd";

        /**
         * 日期时间(日期点号分割)
         */
        public final static String DATA_TIME_DOT = "yyyy.MM.dd HH:mm:ss";

        /**
         * 日期时间(日期反斜杠)
         */
        public final static String DATETIME_SLASH = "yyyy/MM/dd HH:mm:ss";

        /**
         * 日期时间(日期横杠)
         */
        public final static String DATETIME_HORIZONTAL = "yyyy-MM-dd HH:mm:ss";
    }

}
