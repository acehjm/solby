package me.solby.ifile.icsv.annotation;

import java.lang.annotation.*;

/**
 * @author majhdk
 * @DESCRIPTION 文件对象注解
 * @date 2018-11-29
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CsvFileDesc {

    String charset() default "utf-8";

    String ignoreRows() default "1";

    String columnSpliter() default ",";

}
