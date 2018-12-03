package me.solby.ifile.iexcel.annotation;

import java.lang.annotation.*;

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
    String title() default "";

    /**
     * Excel中的顺序
     * 【1。最好是连续的数字，如：1，2，3】
     */
    int order() default 0;

}
