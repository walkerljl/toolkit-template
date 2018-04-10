package org.walkerljl.toolkit.template.cache;

import java.util.concurrent.atomic.AtomicInteger;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CachedDataAccessTemplateTest
 *
 * @author xingxun
 */
public class CachedDataAccessTemplateTest {

    @Test
    public void testForInvalidParams() {
        String actual = new CachedDataAccessTemplate<String>() {
            @Override
            public CacheSource<String> getCacheSource() {
                return null;
            }

            @Override
            public String loadDataFromDataSource(Object... params) {
                return null;
            }
        }.get(null);
        Assert.assertNull(actual);

        new CachedDataAccessTemplate<String>() {
            @Override
            public CacheSource<String> getCacheSource() {
                return null;
            }

            @Override
            public String loadDataFromDataSource(Object... params) {
                return null;
            }
        }.get(new Object[]{});
        Assert.assertNull(actual);
    }

    @Test
    public void testForNullCacheSource() {

        String actual = new CachedDataAccessTemplate<String>() {
            @Override
            public CacheSource<String> getCacheSource() {
                return null;
            }

            @Override
            public String loadDataFromDataSource(Object... params) {
                return null;
            }
        }.get(new Object[] {"hello"});
        Assert.assertNull(actual);
    }

    @Test
    public void testForCacheKeyIsBlank() {

        String actual = new CachedDataAccessTemplate<String>() {
            @Override
            public CacheSource<String> getCacheSource() {
                return new CacheSource<String>() {
                    @Override
                    public String buildKey(Object... params) {
                        return null;
                    }

                    @Override
                    public String get(String key) {
                        return null;
                    }

                    @Override
                    public boolean keyIsExists(String key) {
                        return false;
                    }

                    @Override
                    public void set(String key, String object) {

                    }

                    @Override
                    public void setKey(String key) {

                    }
                };
            }

            @Override
            public String loadDataFromDataSource(Object... params) {
                return null;
            }
        }.get(new Object[] {"hello"});
        Assert.assertNull(actual);

    }

    @Test
    public void testForValidCacheObject() {

        String expected = "world";
        String actual = new CachedDataAccessTemplate<String>() {
            @Override
            public CacheSource<String> getCacheSource() {
                return new CacheSource<String>() {
                    @Override
                    public String buildKey(Object... params) {
                        return String.valueOf(params[0]);
                    }

                    @Override
                    public String get(String key) {
                        return expected;
                    }

                    @Override
                    public boolean keyIsExists(String key) {
                        return false;
                    }

                    @Override
                    public void set(String key, String object) {

                    }

                    @Override
                    public void setKey(String key) {

                    }
                };
            }

            @Override
            public String loadDataFromDataSource(Object... params) {
                return null;
            }
        }.get(new Object[] {"hello"});
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testForInvalidCacheObjectAndKeyIsExists() {

        String actual = new CachedDataAccessTemplate<String>() {
            @Override
            public CacheSource<String> getCacheSource() {
                return new CacheSource<String>() {
                    @Override
                    public String buildKey(Object... params) {
                        return String.valueOf(params[0]);
                    }

                    @Override
                    public String get(String key) {
                        return null;
                    }

                    @Override
                    public boolean keyIsExists(String key) {
                        return true;
                    }

                    @Override
                    public void set(String key, String object) {

                    }

                    @Override
                    public void setKey(String key) {

                    }
                };
            }

            @Override
            public String loadDataFromDataSource(Object... params) {
                return null;
            }
        }.get(new Object[] {"hello"});
        Assert.assertNull(actual);
    }

    @Test
    public void testForInvalidCacheObjectAndKeyNotExists() {

        final AtomicInteger setKeyCounter = new AtomicInteger(0);
        String actual = new CachedDataAccessTemplate<String>() {
            @Override
            public CacheSource<String> getCacheSource() {
                return new CacheSource<String>() {
                    @Override
                    public String buildKey(Object... params) {
                        return String.valueOf(params[0]);
                    }

                    @Override
                    public String get(String key) {
                        return null;
                    }

                    @Override
                    public boolean keyIsExists(String key) {
                        return false;
                    }

                    @Override
                    public void set(String key, String object) {

                    }

                    @Override
                    public void setKey(String key) {
                        setKeyCounter.incrementAndGet();
                    }
                };
            }

            @Override
            public String loadDataFromDataSource(Object... params) {
                return null;
            }
        }.get(new Object[] {"hello"});
        Assert.assertNull(actual);
        Assert.assertEquals(setKeyCounter.get(), 1);
    }

    @Test
    public void testForInvalidCacheObjectAndKeyNotExists2() {

        String expected = "world";
        final AtomicInteger setCounter = new AtomicInteger(0);
        String actual = new CachedDataAccessTemplate<String>() {
            @Override
            public CacheSource<String> getCacheSource() {
                return new CacheSource<String>() {
                    @Override
                    public String buildKey(Object... params) {
                        return String.valueOf(params[0]);
                    }

                    @Override
                    public String get(String key) {
                        return null;
                    }

                    @Override
                    public boolean keyIsExists(String key) {
                        return false;
                    }

                    @Override
                    public void set(String key, String object) {
                        setCounter.incrementAndGet();
                    }

                    @Override
                    public void setKey(String key) {
                    }
                };
            }

            @Override
            public String loadDataFromDataSource(Object... params) {
                return expected;
            }
        }.get(new Object[] {"hello"});
        Assert.assertEquals(actual, expected);
        Assert.assertEquals(setCounter.get(), 1);
    }

    @Test
    public void test() {

        new CachedDataAccessTemplate<String>() {
            @Override
            public CacheSource<String> getCacheSource() {
                return null;
            }

            @Override
            public String loadDataFromDataSource(Object... params) {
                return null;
            }
        }.get(new Object[]{"hello"});
    }
}