package com.hccake.simpleredis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Hccake
 * @version 1.0
 * @date 2020/3/20 16:56
 */
@ConfigurationProperties(prefix = "simple.cache")
public class SimpleCacheProperties {

    /**
     * redis锁 后缀
     */
    private String lockKeySuffix = "locked";
    /**
     * 默认分隔符
     */
    private String delimiter= ":";
    /**
     * 空值标识
     */
    private String nullValue = "N_V";
    /**
     * 默认超时时间(s)
     */
    private long expireTime = 86400L;
    /**
     * 锁的超时时间(ms)
     */
    private long lockedTimeOut = 1000L;

    public String getLockKeySuffix() {
        return lockKeySuffix;
    }

    public void setLockKeySuffix(String lockKeySuffix) {
        this.lockKeySuffix = lockKeySuffix;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getNullValue() {
        return nullValue;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public long getLockedTimeOut() {
        return lockedTimeOut;
    }

    public void setLockedTimeOut(long lockedTimeOut) {
        this.lockedTimeOut = lockedTimeOut;
    }
}
