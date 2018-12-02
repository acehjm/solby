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

    String title() default "";

    int order() default 0;

}
