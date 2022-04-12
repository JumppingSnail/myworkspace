package cn.com.metamedical.util.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存时间默认为"medium",取值"short"(或 "s"),"medium"(或 "m"),"long"(或 "l")
 * 使用方式：@DataCacheAnnotation("l")
 * */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataCacheAnnotation {
	String value() default "medium";
	boolean cloneFlag() default false;
}
