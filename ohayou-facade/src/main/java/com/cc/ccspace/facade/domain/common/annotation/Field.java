package com.cc.ccspace.facade.domain.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/5/7 0:48.
 */
@Target({ElementType.METHOD, ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
     boolean isKey() default false;
    String type() default "char";
    boolean isNull() default true;
    String comment() default"";
    String len() default "(10)";

}
