package com.std.likou;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxiangwu
 * @title: LFUCache
 * @projectName ThreadDemo
 * @date 2021/3/22 14:02
 */
public class LRUCache_1 {

    public static void main(String[] args) {
        LRUCache_1 cache_1 = new LRUCache_1(2);
        cache_1.put(1,1);
        cache_1.put(2, 2);
        System.out.println(cache_1.get(1));
        cache_1.put(3,3);
        System.out.println(cache_1.get(2));
        cache_1.put(4,4);
        System.out.println(cache_1.get(1));
        System.out.println(cache_1.get(3));
        System.out.println(cache_1.get(4));

    }
    private Map<Integer, Node> cache = new HashMap<>();
    private int size, capacity;
    private Node head, tail;

    public LRUCache_1(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) {
            return -1;
        }

        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = cache.get(key);
        if (node == null) {
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addToHead(newNode);
            ++size;
            if (size > capacity) {
                Node tail = removeTail();
                cache.remove(tail.key);
                --size;
            }
        } else {
            node.value = value;
            moveToHead(node);
        }
    }

    private void addToHead(Node node) {
        node.next = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void moveToHead(Node node) {
       removeNode(node);
       addToHead(node);
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private Node removeTail(){
        Node node = tail.prev;
        removeNode(node);
        return node;
    }
}

class Node {
    public int key, value;
    public Node next, prev;

    public Node() {
    }

    public Node(int k, int v) {
        this.key = k;
        this.value = v;
    }
}

