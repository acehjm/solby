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

    /**
     * 编码格式，暂时未使用，后续完善
     */
    String charset() default "utf-8";

    /**
     * 忽略的行，也是数据开始行
     */
    int ignoreRows() default 0;

}
