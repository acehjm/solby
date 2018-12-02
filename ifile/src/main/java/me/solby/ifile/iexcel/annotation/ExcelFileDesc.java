package me.solby.ifile.iexcel.annotation;

import java.lang.annotation.*;

/**
 * @author majhdk
 * @DESCRIPTION Excel文件描述
 * @date 2018-12-02
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelFileDesc {

    String charset() default "utf-8";  //todo add after

    int ignoreRows() default 0;

}
