package org.walkerljl.toolkit.template.cache;

/**
 * 可被击穿的缓存数据源
 *
 * @author lijunlin
 */
public abstract class StrikedCacheSource<T> implements CacheSource<T> {

    @Override
    public boolean keyIsExists(String key) {
        return false;
    }

    @Override
    public void setKey(String key) {
        //do nothing
    }
}
