# 1.x 版本
> 0.x 版本中提供了批量操作得缓存注解，实际使用中，对于批量操作时的分布式锁，处理的不那么友好，
>实际开发时，批量操作应用的也比较少，所以在 1.x 版本中移除了批量操作功能

### 如何使用
1. 引入依赖
```xml
<dependency>
    <groupId>com.hccake</groupId>
    <artifactId>simple-cache</artifactId>
    <version>${lastVersion}</version>
</dependency>
```
    
2. 配置文件    
> 注：底层目前基于 spring-data-redis， 所以需要配置spring-data-redis的属性
> 如 redis的host、port等基本信息      
```yaml
spring:
  redis:
    host: your-host
    password: ''
    port: 6379
```

目前工具类本身提供的配置有以下几种：
```yaml
simple:
    cahce:
        lockKeySuffix: locked  # redis锁 后缀
        delimiter: :     # 默认分隔符
        nullValue: N_V   # 空值标识
        expireTime: 86400L # 默认超时时间(s)
        lockedTimeOut: 1000L # 锁的超时时间(ms)
```

3. 开启使用
在配置类上添加注解`@EnableSimpleCache`, 即可开启使用
                                            
```java
@EnableSimpleRedis
@SpringBootApplication
public class SimpleCacheDemoApplication {   
    public static void main(String[] args) {
        SpringApplication.run(SimpleCacheDemoApplication.class, args);
    }
}
```

4. 方法注解

在service实现类的方法上添加注解即可

4.1. @Cached  
    - 先查询缓存 若不为空 直接返回  
    - 若缓存为空，则调用代理方法，获取结果集  
    - 将结果集置入缓存，以便下次读取  
    
```java
    @Cached(key = "redisKey here", keyJoint = "#p0 + 'lisi'", ttl="86400")
    public User getUser(String userName) {
        return new User("zhangsan", 18);
    }
```
key：redis缓存的Key
keyJoint： 为非必传参数，如果key是动态key, 则需传入此参数，值为spel表达式
，方法执行时，会自动解析，并将其和key拼接，默认使用：作为分隔符
ttl：redis的缓存过期时间，默认使用全局配置时间

4.2. @CachedPut
    - 执行代理方法
    - 将代理方法的返回结果置入缓存
    
4.3. @CacheDel
    - 执行代理方法
    - 将对应缓存删除


---
# 0.x 版本 
##  使用注解操作redis缓存，目前支持String 以及 Hash类型

### 注解操作类型说明
> 目前分为三种通用的操作类型

1. Cached
    1. 先查询缓存 若不为空 直接返回
    2. 若缓存为空，则调用代理方法，获取结果集
    3. 将结果集置入缓存，以便下次读取
    
2. CachedPut
    1. 执行代理方法
    2. 将代理方法的返回结果置入缓存
    
3. CacheDel
    1. 执行代理方法
    2. 将对应缓存删除
    
    
### 如何使用

1. 准备工作  
    引入依赖后，在配置类上添加注解`@EnableSimpleCache`, 即可开启使用
    > 注：redis 配置按照 spring-data-redis， 如果已经配置过，无需改动

    ```java
    @EnableSimpleRedis
    @SpringBootApplication
    public class SimpleCacheDemoApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(SimpleCacheDemoApplication.class, args);
        }
    }
    ```

2. 在service实现类的方法上添加注解即可

    ```java
    @CacheForString(type = OpType.CACHED, key = "redisKey here", keyJoint = "#p0 + 'lisi'")
    public User getUser(String userName){
        return new User("zhangsan", 18);
    }
    ```

    其中type为操作类型， key为redisKey, keyJoint为非必传参数，如果key是动态key, 则需传入此参数，值为spel表达式
    ，方法执行时，会自动解析，并将其和key前缀拼接，使用：作为分隔符