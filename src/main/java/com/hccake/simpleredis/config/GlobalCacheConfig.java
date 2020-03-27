package com.hccake.simpleredis.config;

/**
 * @author Hccake
 * @version 1.0
 * @date 2020/3/20 16:56
 */
public class GlobalCacheConfig {
    private static SimpleCacheProperties simpleCacheProperties;
    private static boolean isInit = false;

    public static void init(SimpleCacheProperties simpleCacheProperties){
        if (isInit){
           throw new RuntimeException("cannot initialize multiple times");
        }
        isInit = true;
        GlobalCacheConfig.simpleCacheProperties = simpleCacheProperties;
    }

    public static String lockKeySuffix() {
        return simpleCacheProperties.getLockKeySuffix();
    }

    public static String delimiter() {
        return simpleCacheProperties.getDelimiter();
    }

    public static String nullValue() {
        return simpleCacheProperties.getNullValue();
    }

    public static long expireTime() {
        return simpleCacheProperties.getExpireTime();
    }

    public static long lockedTimeOut() {
        return simpleCacheProperties.getLockedTimeOut();
    }

}
