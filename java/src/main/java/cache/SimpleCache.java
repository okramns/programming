package cache;

import java.util.HashMap;

/**
 * Created by OSingh4 on 2/19/2018.
 */
public class SimpleCache<K,V> implements cache.Cache<K,V> {

    private HashMap<K,CacheObject> map;
    private int maxSize;
    private int cacheSize;
    private LRUQueue queue;


    public SimpleCache(int size){
        this.maxSize = size;
        map = new HashMap<>(size);
        queue = new LRUQueue();
    }

    @Override
    public V get(K key) {
        CacheObject object =  map.get(key);
        if(object != null){
            queue.update(object);
            return object.value;
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        if(cacheSize < maxSize){
            insertValue(key,value);
            cacheSize++;
        }else{
            K replacedKey = getKeyToReplace();
            map.remove(replacedKey);
            insertValue(key,value);
        }
    }

    private void insertValue(K key,V value){
        CacheObject cacheObject = new CacheObject();
        cacheObject.key = key;
        cacheObject.value = value;
        map.put(key,cacheObject);
        queue.insert(cacheObject);

    }

    private K getKeyToReplace() {
        return queue.removeLRU();
    }


    private class CacheObject{
        K key;
        V value;
        CacheObject previous;
        CacheObject next;
    }

    private class LRUQueue {

        CacheObject head;
        CacheObject tail;

        void update(CacheObject object){

            if(head == object){
                return;
            }

            if(object == tail){
                tail = object.previous;
            }

            //remove the object
            if(object.previous != null) {
                object.previous.next = object.next;
            }

            if(object.next != null) {
                object.next.previous = object.previous;
            }

            //insert at head
            head.previous = object;
            head = object;
            object.next = head;
            object.previous = null;

        }

        void insert(CacheObject object){
            object.next = head;
            if(head != null) {
                head.previous = object;
            }
            head = object;

            if(tail == null){
                tail = head;
            }
        }

        K removeLRU(){
            K key = tail.key;
            tail = tail.previous;
            if(tail != null) {
                tail.next = null;
            }
            return key;
        }
    }
}
