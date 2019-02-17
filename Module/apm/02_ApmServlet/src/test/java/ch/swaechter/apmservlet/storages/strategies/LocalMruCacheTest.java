package ch.swaechter.apmservlet.storages.strategies;

import ch.swaechter.apmservlet.sdk.Storage;
import ch.swaechter.apmservlet.storages.local.LocalStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class LocalMruCacheTest {

    private Storage backendStorage;

    private LocalMruCache localMruCache;

    @BeforeEach
    public void setupCache() {
        // Create the local storage and the local MRU cache
        backendStorage = new LocalStorage();
        localMruCache = new LocalMruCache(backendStorage, 5);
    }

    @Test
    public void testStorage() {
        // Fill some values
        storeAndLoad(1000); // 1000
        storeAndLoad(1001); // 1001 1000
        storeAndLoad(1002); // 1002 1001 1000
        Assertions.assertEquals(Arrays.asList(1000, 1001, 1002), localMruCache.getCacheIds());

        // Fill up
        storeAndLoad(1003); // 1003 1002 1001 1000
        storeAndLoad(1004); // 1004 1003 1002 1001 1000
        Assertions.assertEquals(Arrays.asList(1000, 1001, 1002, 1003, 1004), localMruCache.getCacheIds());

        // Override the newest value
        storeAndLoad(1005); // 1005 1003 1002 1001 1000
        Assertions.assertEquals(Arrays.asList(1000, 1001, 1002, 1003, 1005), localMruCache.getCacheIds());

        // Override the oldest value
        storeAndLoad(1006); // 1006 1003 1002 1001 1000
        Assertions.assertEquals(Arrays.asList(1000, 1001, 1002, 1003, 1006), localMruCache.getCacheIds());

        // Reuse a cold value
        storeAndLoad(1000); // 1000 1006 1003 1002 1001
        Assertions.assertEquals(Arrays.asList(1000, 1001, 1002, 1003, 1006), localMruCache.getCacheIds());

        // Reuse a hot value
        load(1003); // 1003 1000 1006 1002 1001
        Assertions.assertEquals(Arrays.asList(1000, 1001, 1002, 1003, 1006), localMruCache.getCacheIds());
        storeAndLoad(1007);  // 1007 1000 1006 1002 1001
        Assertions.assertEquals(Arrays.asList(1000, 1001, 1002, 1006, 1007), localMruCache.getCacheIds());
    }

    private void load(int id) {
        try {
            // Simulate a small delay so no entries have the same last accessed time
            Thread.sleep(10);
            localMruCache.load(id);
        } catch (InterruptedException exception) {
            throw new RuntimeException("Unable to sleep");
        }
    }

    private void storeAndLoad(int id) {
        try {
            // Simulate a small delay so no entries have the same last accessed time
            Thread.sleep(10);
            localMruCache.store(id, "Data");
            localMruCache.load(id);
        } catch (InterruptedException exception) {
            throw new RuntimeException("Unable to sleep");
        }
    }
}
