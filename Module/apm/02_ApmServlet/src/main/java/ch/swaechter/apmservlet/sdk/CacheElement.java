package ch.swaechter.apmservlet.sdk;

public class CacheElement {

    private final int id;

    private final String value;

    private long lastAccessed;

    public CacheElement(String value) {
        this.id = -1;
        this.value = value;
        this.lastAccessed = System.currentTimeMillis();
    }

    public CacheElement(int id, String value) {
        this.id = id;
        this.value = value;
        this.lastAccessed = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public long getLastAccessed() {
        return lastAccessed;
    }

    public void updateLastAccessed() {
        this.lastAccessed = System.currentTimeMillis();
    }
}
