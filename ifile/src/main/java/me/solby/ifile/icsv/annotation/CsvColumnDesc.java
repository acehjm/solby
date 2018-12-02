package me.solby.ifile.icsv.annotation;

import java.lang.annotation.*;

/**
 * @author majhdk
 * @DESCRIPTION 文件列注解
 * @date 2018-11-29
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CsvColumnDesc {

    int no() default 0;

    boolean isIndex() default false;
}
