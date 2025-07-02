package moshi.blossom.util;

public class Pair<T, K> {
    T key;
    K value;

    public Pair(T key, K value) {
        this.key = key;
        this.value = value;
    }

    public T getKey() {
        return this.key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public K getValue() {
        return this.value;
    }

    public void setValue(K value) {
        this.value = value;
    }
}