package org.walkerljl.toolkit.template.cache;

import java.util.concurrent.atomic.AtomicInteger;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * StrikedCacheSourceTest
 *
 * @author xingxun
 */
public class StrikedCacheSourceTest {

    @Test
    public void test() {

        AtomicInteger getOperationCounter = new AtomicInteger(0);
        StrikedCacheSource strikedCacheSource = new TestedStrikedCacheSource(getOperationCounter);

        CachedDataAccessTemplate<String> cachedDataAccessTemplate = new CachedDataAccessTemplate<String>() {

            @Override
            public CacheSource getCacheSource() {
                return strikedCacheSource;
            }

            @Override
            public String loadDataFromDataSource(Object... params) {
                return null;
            }
        };

        String actual = cachedDataAccessTemplate.get(new Object[]{"hello"});
        Assert.assertNull(actual);
        Assert.assertEquals(getOperationCounter.get(), 1);

        actual = cachedDataAccessTemplate.get(new Object[]{"hello"});
        Assert.assertNull(actual);
        Assert.assertEquals(getOperationCounter.get(), 2);
    }
}

class TestedStrikedCacheSource extends StrikedCacheSource {

    private AtomicInteger getOperationCounter;

    public TestedStrikedCacheSource(AtomicInteger getOperationCounter) {
        this.getOperationCounter = getOperationCounter;
    }

    @Override
    public String buildKey(Object... params) {
        return String.valueOf(params[0]);
    }

    @Override
    public Object get(String key) {
        getOperationCounter.incrementAndGet();
        return null;
    }

    @Override
    public void set(String key, Object object) {

    }
}