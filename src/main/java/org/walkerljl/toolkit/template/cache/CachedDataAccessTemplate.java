package org.walkerljl.toolkit.template.cache;

import com.alibaba.fastjson.JSON;

import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.logging.LoggerFactory;

/**
 * 缓存数据访问模板
 *
 * @author lijunlin
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
        if (params == null || params.length == 0) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Params is null or length is zero,return null.");
            }
            return null;
        }
        String paramsString = JSON.toJSONString(params);
        CacheSource<T> cacheSource = getCacheSource();
        if (cacheSource == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("CacheSource is null,return null,param is %s.", paramsString));
            }
            return null;
        }
        String cacheKey = cacheSource.buildKey(params);
        T object = cacheSource.get(cacheKey);
        if (object != null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Hits cache,key is %s,param is %s,data is %s.",
                        cacheKey, paramsString, JSON.toJSONString(object)));
            }
            return object;
        }
        if (cacheSource.keyIsExists(cacheKey)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Cache data is null,but cache key is exists,return null," +
                                "cache key is %s,param is %s.",
                        cacheKey, paramsString));
            }
            return null;
        }

        object = loadDataFromDataSource(params);
        if (object == null) {
            cacheSource.setKey(cacheKey);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Data is null from dataSource,set key %s to cache,param is %s.",
                        cacheKey, paramsString));
            }
        } else {
            cacheSource.set(cacheKey, object);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Push data to cache which comes from dataSource,param is %s,data is %s.",
                        paramsString, JSON.toJSONString(object)));
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Hits dataSource,param is %s,data is %s.",
                    paramsString, JSON.toJSONString(object)));
        }
        return object;
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
