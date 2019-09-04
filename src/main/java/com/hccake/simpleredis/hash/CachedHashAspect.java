package com.hccake.simpleredis.hash;

import com.hccake.simpleredis.RedisHelper;
import com.hccake.simpleredis.core.CacheOps;
import com.hccake.simpleredis.core.KeyGenerator;
import com.hccake.simpleredis.core.TemplateMethod;
import com.hccake.simpleredis.function.ResultMethod;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author Hccake
 * @version 1.0
 * @date 2019/8/31 18:01
 */
@Aspect
@Component
@Slf4j
public class CachedHashAspect {

    @Resource
    private RedisHelper redisHelper;


    @Pointcut("@annotation(com.hccake.simpleredis.Cached)")
    public void cacheHashPointCut() {
    }


    @Around("cacheHashPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //获取目标方法
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        log.debug("=======The hash cache aop is executed! method : {}", method.getName());

        //方法返回值
        Class<?> returnType = method.getReturnType();

        //根据方法的参数 以及当前类对象获得 keyGenerator
        Object target = point.getTarget();
        Object[] arguments = point.getArgs();
        KeyGenerator keyGenerator = new KeyGenerator(target, method, arguments);

        // 织入方法
        ResultMethod<Object> pointMethod = CacheOps.genPointMethodByPoint(point);


        //获取注解对象
        CacheForHash cacheForHash = AnnotationUtils.getAnnotation(method, CacheForHash.class);

        //获取操作类
        Assert.notNull(cacheForHash, "CacheForHash cloud not null.");
        CacheOps ops = new OpsForHash(cacheForHash, keyGenerator, pointMethod, returnType, redisHelper);

        //执行对应模板方法
        return TemplateMethod.runByOpType(ops, cacheForHash.type());

    }


}
