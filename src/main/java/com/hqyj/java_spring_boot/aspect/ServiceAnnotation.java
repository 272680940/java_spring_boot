package com.hqyj.java_spring_boot.aspect;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented//文档型
@Retention(RetentionPolicy.RUNTIME)//指明环境
public @interface ServiceAnnotation {
    String value() default  "aaa";
}
