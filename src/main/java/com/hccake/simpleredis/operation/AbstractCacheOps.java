package com.hccake.simpleredis.operation;

import com.hccake.simpleredis.config.GlobalCacheProperties;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author Hccake
 * @version 1.0
 * @date 2019/9/2 15:19
 */
public abstract class AbstractCacheOps {

    public AbstractCacheOps(ProceedingJoinPoint joinPoint){
        this.joinPoint = joinPoint;
    }

    /**
     * 织入方法
     *
     * @return ResultMethod
     */
    private ProceedingJoinPoint joinPoint;

    public ProceedingJoinPoint joinPoint() {
        return joinPoint;
    }

    /**
     * 检查缓存数据是否是空值
     *
     * @param cacheData
     * @return
     */
    public boolean nullValue(Object cacheData) {
        return GlobalCacheProperties.nullValue().equals(cacheData);
    }


}
