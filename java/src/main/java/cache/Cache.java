package cache;

/**
 * Created by OSingh4 on 2/19/2018.
 */
public interface Cache<K,V> {

    public V get(K key);
    public void put(K key,V value);
}
