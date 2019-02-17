package ch.swaechter.apmservlet.storages.strategies;

import ch.swaechter.apmservlet.sdk.Storage;
import ch.swaechter.apmservlet.storages.local.LocalStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class LocalFifoCacheTest {

    private Storage backendStorage;

    private LocalFifoCache localFifoCache;

    @BeforeEach
    public void setupCache() {
        // Create the local storage and the local FIFO cache
        backendStorage = new LocalStorage();
        localFifoCache = new LocalFifoCache(backendStorage, 5);
    }

    @Test
    public void testStorage() {
        // Fill some values
        storeAndLoad(1000); // 1000
        storeAndLoad(1001); // 1000 1001
        storeAndLoad(1002); // 1000 1001 1002
        Assertions.assertEquals(Arrays.asList(1000, 1001, 1002), localFifoCache.getCacheIds());

        // Fill up
        storeAndLoad(1003); // 1000 1001 1002 1003
        storeAndLoad(1004); // 1000 1001 1002 1003 1004
        Assertions.assertEquals(Arrays.asList(1000, 1001, 1002, 1003, 1004), localFifoCache.getCacheIds());

        // Override the oldest value
        storeAndLoad(1005); // 1005 1001 1002 1003 1004
        Assertions.assertEquals(Arrays.asList(1005, 1001, 1002, 1003, 1004), localFifoCache.getCacheIds());

        // Override the oldest value
        storeAndLoad(1006); // 1005 1006 1002 1003 1004
        Assertions.assertEquals(Arrays.asList(1005, 1006, 1002, 1003, 1004), localFifoCache.getCacheIds());

        // Reuse a cold value
        storeAndLoad(1000); // 1005 1006 1000 1003 1004
        Assertions.assertEquals(Arrays.asList(1005, 1006, 1000, 1003, 1004), localFifoCache.getCacheIds());

        // Reuse a hot value
        load(1003); // 1005 1006 1000 1003 1004
        Assertions.assertEquals(Arrays.asList(1005, 1006, 1000, 1003, 1004), localFifoCache.getCacheIds());
        storeAndLoad(1007); // 1005 1006 1000 1007 1003
        Assertions.assertEquals(Arrays.asList(1005, 1006, 1000, 1007, 1004), localFifoCache.getCacheIds());
    }

    private void load(int id) {
        try {
            // Simulate a small delay so no entries have the same last accessed time
            Thread.sleep(10);
            localFifoCache.load(id);
        } catch (InterruptedException exception) {
            throw new RuntimeException("Unable to sleep");
        }
    }

    private void storeAndLoad(int id) {
        try {
            // Simulate a small delay so no entries have the same last accessed time
            Thread.sleep(10);
            localFifoCache.store(id, "Data");
            localFifoCache.load(id);
        } catch (InterruptedException exception) {
            throw new RuntimeException("Unable to sleep");
        }
    }
}
