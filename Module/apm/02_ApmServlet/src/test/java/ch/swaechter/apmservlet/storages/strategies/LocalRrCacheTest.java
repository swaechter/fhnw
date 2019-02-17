package ch.swaechter.apmservlet.storages.strategies;

import ch.swaechter.apmservlet.sdk.Storage;
import ch.swaechter.apmservlet.storages.local.LocalStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class LocalRrCacheTest {

    private Storage backendStorage;

    private LocalRrCache localRrCache;

    @BeforeEach
    public void setupCache() {
        // Create the local storage and the local RR cache
        backendStorage = new LocalStorage();
        localRrCache = new LocalRrCache(backendStorage, 5);
    }

    @Test
    public void testStorage() {
        // Fill some values
        storeAndLoad(1000); // 1000
        storeAndLoad(1001); // 1000 1001
        storeAndLoad(1002); // 1000 1001 1002
        Assertions.assertEquals(Arrays.asList(1000, 1001, 1002), localRrCache.getCacheIds());

        // Fill up
        storeAndLoad(1003); // 1000 1001 1002 1003
        storeAndLoad(1004); // 1000 1001 1002 1003 1004
        Assertions.assertEquals(Arrays.asList(1000, 1001, 1002, 1003, 1004), localRrCache.getCacheIds());

        // I can't test randomness - so just generate a few values ensure we don't crash \_(ツ)_/¯
        for (int i = 1000; i < 1010; i++) {
            storeAndLoad(i);
        }
    }

    private void storeAndLoad(int id) {
        try {
            // Simulate a small delay so no entries have the same last accessed time
            Thread.sleep(10);
            localRrCache.store(id, "Data");
            localRrCache.load(id);
        } catch (InterruptedException exception) {
            throw new RuntimeException("Unable to sleep");
        }
    }
}
