package cache;

import org.junit.Test;

import static org.junit.Assert.*;

import cache.SimpleCacheTest;
import cache.Cache;

/**
 * Created by OSingh4 on 2/19/2018.
 */
public class SimpleCacheTest {

    @Test
    public void testPutOnEmptyCache() throws Exception {

        Cache<String,String> cache = new SimpleCache(1);
        cache.put("A","Apple");
        assertEquals("Apple",cache.get("A"));
    }

    @Test
    public void testGetOnEmptyCache() throws Exception {
        Cache<String,String> cache = new SimpleCache(1);
        assertNull(cache.get("A"));
    }

    @Test
    public void checkPutOnFullCache() throws Exception{
        Cache<String,String> cache = new SimpleCache(1);
        cache.put("A","Apple");
        cache.put("B","Bat");
        assertEquals("Bat",cache.get("B"));
        assertNull(cache.get("A"));
    }

    @Test
    public void checkLRUWithoutUpdate() throws Exception{
        Cache<String,String> cache = new SimpleCache(2);
        cache.put("A","Apple");
        cache.put("B","Bat");
        cache.put("C","Cat");

        assertEquals("Bat",cache.get("B"));
        assertEquals("Cat",cache.get("C"));
        assertNull(cache.get("A"));

    }

    @Test
    public void checkLRUWithUpdate() throws Exception{
        Cache<String,String> cache = new SimpleCache(2);
        cache.put("A","Apple");
        cache.put("B","Bat");

        cache.get("A");
        cache.put("C","Cat");

        assertEquals("Apple",cache.get("A"));
        assertEquals("Cat",cache.get("C"));
        assertNull(cache.get("B"));

    }

    @Test
    public void checkLRUUpdateAfterAccessingAnItemInMiddle() throws Exception{
        Cache<String,String> cache = new SimpleCache(3);
        cache.put("A","Apple");
        cache.put("B","Bat");
        cache.put("C","Cat");

        cache.get("B");

        cache.put("D","Duck");
        cache.put("E","Eagle");

        assertEquals("Bat",cache.get("B"));
        assertEquals("Duck",cache.get("D"));
        assertEquals("Eagle",cache.get("E"));
        assertNull(cache.get("A"));
        assertNull(cache.get("C"));
    }

}