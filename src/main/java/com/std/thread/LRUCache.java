package com.std.thread;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author chenxiangwu
 * @title: LRUCache
 * @projectName ThreadDemo
 * @date 2020/12/18 20:38
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int CACHE_SIZE ;

    public LRUCache(int cacheSize){
        super((int) (Math.ceil(cacheSize/ 0.75 )+1), 0.75f, true);
        CACHE_SIZE = cacheSize;
    }

   protected boolean removeEldestEntry(Map<K, V> eldest){
        return size() > CACHE_SIZE;
   }

}
