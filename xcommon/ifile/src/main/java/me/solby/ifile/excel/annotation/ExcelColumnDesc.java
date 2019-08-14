package me.solby.ifile.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author majhdk
 * @DESCRIPTION Excel列描述
 * @date 2018-12-01
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelColumnDesc {

    /**
     * 标题名称
     */
    String column() default "";

    /**
     * Excel中列的顺序
     * 【1。最好是连续的数字，如：1，2，3】
     * 【2。数值越大越靠后】
     */
    int order() default 0;

    /**
     * 列对象类型
     */
    Class<?> type() default String.class;

}
