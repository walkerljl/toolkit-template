package org.walkerljl.toolkit.template.cache;

import com.alibaba.fastjson.JSON;

import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;

/**
 * 缓存数据访问模板
 *
 * @author xingxun
 */
public abstract class CachedDataAccessTemplate<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachedDataAccessTemplate.class);

    /**
     * 查询数据
     *
     * @param params 查询参数
     * @return
     */
    public T get(Object... params) {
        if (isEmpty(params)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Params is null or empty,return null.");
            }
            return null;
        }
        CacheSource<T> cacheSource = getCacheSource();
        if (cacheSource == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("CacheSource is null,return null,param is %s.", toString(params)));
            }
            return null;
        }
        String cacheKey = cacheSource.buildKey(params);
        if (isBlank(cacheKey)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Cache key is blank,param is %s.", toString(params)));
            }
            return null;
        }
        T cacheValue = cacheSource.get(cacheKey);
        if (cacheValue != null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Hits cache,key is %s,param is %s,value is %s.",
                        cacheKey, toString(params), toString(cacheValue)));
            }
            return cacheValue;
        }
        if (cacheSource.keyIsExists(cacheKey)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Cache value is null,but cache key is exists,return null," +
                                "cache key is %s,param is %s.",
                        cacheKey, toString(params)));
            }
            return null;
        }

        cacheValue = loadDataFromDataSource(params);
        if (cacheValue == null) {
            cacheSource.setKey(cacheKey);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Cache value is null from dataSource,set key %s to cache,param is %s.",
                        cacheKey, toString(params)));
            }
        } else {
            cacheSource.set(cacheKey, cacheValue);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Push cache value to cache which comes from dataSource,param is %s,value is %s.",
                        toString(params), toString(cacheValue)));
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Hits dataSource,param is %s,value is %s.",
                    toString(params), toString(cacheValue)));
        }
        return cacheValue;
    }

    /**
     * 是否为空
     *
     * @param array 数组
     * @param <E>
     * @return
     */
    private <E> boolean isEmpty(E[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 是否为空
     *
     * @param string String
     * @return
     */
    private boolean isBlank(String string) {
        return string == null || "".equalsIgnoreCase(string);
    }

    /**
     * ToString
     *
     * @param object 被ToString的对象
     * @return
     */
    private String toString(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 获取缓存数据源
     *
     * @return
     */
    public abstract CacheSource<T> getCacheSource();

    /**
     * 从其它数据源加载数据，如数据库
     *
     * @param params 参数列表
     * @return
     */
    public abstract T loadDataFromDataSource(Object... params);
}
