package com.hccake.simpleredis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hccake
 * @version 1.0
 * @date 2019/9/2 14:13
 * 指定扫描包
 */
@Configuration
@ComponentScan("com.hccake.simpleredis.**")
public class SimpleCacheAutoConfiguration {

    /**
     * 初始化配置类
     * @return SimpleCacheProperties
     */
    @Bean
    public SimpleCacheProperties simpleCacheProperties(){
        SimpleCacheProperties simpleCacheProperties = new SimpleCacheProperties();
        // 提供静态方法
        SimpleCacheConfig.init(simpleCacheProperties);
        return simpleCacheProperties;
    }

}
