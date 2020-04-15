package com.hccake.simpleredis.config;

import com.hccake.simpleredis.core.CacheLock;
import com.hccake.simpleredis.serialize.CacheSerializer;
import com.hccake.simpleredis.serialize.JacksonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Hccake
 * @version 1.0
 * @date 2019/9/2 14:13
 * 指定扫描包
 */
@Configuration
public class SimpleCacheAutoConfiguration {

    /**
     * 利用构造注入初始化CacheLock
     * @param redisTemplate 
     */
    SimpleCacheAutoConfiguration(StringRedisTemplate redisTemplate){
        CacheLock.init(redisTemplate);
    }

    /**
     * 初始化配置类
     * @return GlobalCacheProperties
     */
    @Bean
    public GlobalCacheProperties globalCacheProperties(){
        return new GlobalCacheProperties();
    }

    @Bean
    public CacheSerializer cacheSerializer(){
        return new JacksonSerializer();
    }


}
