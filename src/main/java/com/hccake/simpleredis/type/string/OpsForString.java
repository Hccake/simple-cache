package com.hccake.simpleredis.type.string;

import com.hccake.simpleredis.config.SimpleCacheConfig;
import com.hccake.simpleredis.core.CacheOps;
import com.hccake.simpleredis.core.KeyGenerator;
import com.hccake.simpleredis.core.OpType;
import com.hccake.simpleredis.template.function.ResultMethod;
import com.hccake.simpleredis.template.function.VoidMethod;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Hccake
 * @version 1.0
 * @date 2019/9/2 15:33
 */
public class OpsForString extends CacheOps {

    /**
     * 生成并返回一个 OpsForMultiString 对象
     *
     * @param cacheForString 注解
     * @param keyGenerator   key 生成方法
     * @param pointMethod    织入方法
     * @param returnType     返回类型
     * @param redisTemplate    缓存工具类
     */
    public OpsForString(CacheForString cacheForString, KeyGenerator keyGenerator, ResultMethod<Object> pointMethod, Type returnType, StringRedisTemplate redisTemplate) {

        super(redisTemplate, pointMethod, returnType);

        //缓存key
        String key = keyGenerator.getKey(cacheForString.key(), cacheForString.keyJoint());

        //缓存操作类型
        OpType opType = cacheForString.type();

        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        // DEL 操作需要的属性
        if (OpType.DEL.equals(opType)) {
            VoidMethod cacheDel = () -> redisTemplate.delete(key);
            this.setCacheDel(cacheDel);
            return;
        }

        // 失效时间控制
        Consumer<Object> cachePut;
        long ttl = cacheForString.ttl();
        if (ttl < 0) {
            cachePut = value -> operations.set(key, (String) value);
        } else if (ttl == 0) {
            cachePut = value -> operations.set(key, (String) value, SimpleCacheConfig.expireTime(), TimeUnit.SECONDS);
        } else {
            cachePut = value -> operations.set(key, (String) value, ttl, TimeUnit.SECONDS);
        }

        //CACHED缓存需要的属性
        if (OpType.CACHED.equals(opType)) {
            //redis 分布式锁的 key
            String lockKey = key + SimpleCacheConfig.lockKeySuffix();
            this.setLockKey(lockKey);

            Supplier cacheQuery = () -> operations.get(key);
            this.setCacheQuery(cacheQuery);

            this.setCachePut(cachePut);
        }

        // PUT 操作需要的属性
        if (OpType.PUT.equals(opType)) {
            this.setCachePut(cachePut);
        }


    }

}
