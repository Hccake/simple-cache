package com.hccake.simpleredis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Hccake
 * @version 1.0
 * @date 2020/3/20 16:56
 */
@ConfigurationProperties(prefix = "simple.cache")
public class GlobalCacheProperties {
    /**
     * redis锁 后缀
     */
    private static String lockKeySuffix = "locked";
    /**
     * 默认分隔符
     */
    private static String delimiter= ":";
    /**
     * 空值标识
     */
    private static String nullValue = "N_V";
    /**
     * 默认超时时间(s)
     */
    private static long expireTime = 86400L;
    /**
     * 锁的超时时间(ms)
     */
    private static long lockedTimeOut = 1000L;


    public void setLockKeySuffix(String lockKeySuffix) {
        GlobalCacheProperties.lockKeySuffix = lockKeySuffix;
    }

    public void setDelimiter(String delimiter) {
        GlobalCacheProperties.delimiter = delimiter;
    }

    public void setNullValue(String nullValue) {
        GlobalCacheProperties.nullValue = nullValue;
    }

    public void setExpireTime(long expireTime) {
        GlobalCacheProperties.expireTime = expireTime;
    }

    public void setLockedTimeOut(long lockedTimeOut) {
        GlobalCacheProperties.lockedTimeOut = lockedTimeOut;
    }


    public static String lockKeySuffix() {
        return GlobalCacheProperties.lockKeySuffix;
    }

    public static String delimiter() {
        return GlobalCacheProperties.delimiter;
    }

    public static String nullValue() {
        return nullValue;
    }

    public static long expireTime() {
        return GlobalCacheProperties.expireTime;
    }

    public static long lockedTimeOut() {
        return GlobalCacheProperties.lockedTimeOut;
    }


}
