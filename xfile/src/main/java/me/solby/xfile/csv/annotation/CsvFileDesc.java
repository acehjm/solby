package me.solby.xfile.csv.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author majhdk
 * @DESCRIPTION 文件对象注解
 * @date 2018-11-29
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CsvFileDesc {

    /**
     * 字符集，默认utf-8
     *
     * @return
     */
    String charset() default "utf-8";

    /**
     * 跳过行【一般为Header之前】
     * 备注：导出时，该属性不生效
     *
     * @return
     */
    int skipLines() default 0;

    /**
     * 分隔符
     *
     * @return
     */
    char separator() default ',';

}
