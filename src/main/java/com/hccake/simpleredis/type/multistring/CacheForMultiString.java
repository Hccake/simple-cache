 package com.hccake.simpleredis.type.multistring;

import com.hccake.simpleredis.core.OpType;

import java.lang.annotation.*;

/**
 * @author Hccake
 * @version 1.0
 * @date 2019/8/31 16:08
 * 利用Aop, 在方法调用前先查询缓存
 * 若缓存中没有数据，则调用方法本身，并将方法返回值放置入缓存中
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheForMultiString {

    /**
     * 操作缓存的类型
     * @return
     */
    OpType type();

    /**
     * redis 存储的Key名
     */
    String key();

    /**
     * 如果需要在key 后面拼接参数
     * 则传入一个拼接数据的 SpEL 表达式
     */
    String keyJoint() default "";

    /**
     * 参数角标，指定参数必须是集合类型
     */
    int multiBy() default 0;

}
