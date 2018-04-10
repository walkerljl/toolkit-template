package org.walkerljl.toolkit.template.cache;

/**
 * 缓存数据源
 *
 * @author xingxun
 */
public interface CacheSource<T> {

    /**
     * 构建Key
     *
     * @param params 参数列表
     * @return
     */
    String buildKey(Object... params);

    /**
     * 查询数据
     *
     * @param key key
     * @return
     */
    T get(String key);

    /**
     * 判断Key是否存在
     *
     * @param key key
     * @return
     */
    boolean keyIsExists(String key);

    /**
     * 将数据Set到缓存
     *
     * @param key    key
     * @param object 数据对象
     */
    void set(String key, T object);

    /**
     * 设置Key
     *
     * @param key key
     */
    void setKey(String key);
}