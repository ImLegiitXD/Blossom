package moshi.blossom.util;

public class Clock {
    long lastMs;

    public Clock() {
        reset();
    }

    public boolean elapsed(long ms) {
        return (System.currentTimeMillis() - this.lastMs > ms);
    }

    public void reset() {
        this.lastMs = System.currentTimeMillis();
    }
}