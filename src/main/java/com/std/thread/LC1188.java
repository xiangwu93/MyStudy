package com.std.thread;

import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenxiangwu
 * @title: LC1188
 * @projectName ThreadDemo
 * @date 2021/2/20 16:27
 */
public class LC1188 {
    public static void main(String[] args) throws InterruptedException {
        BoundedBlockingQueue queue = new BoundedBlockingQueue(5);
        queue.enqueue(1);
        System.out.println(queue.dequeue());
        queue.enqueue(0);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        System.out.println(queue.dequeue());
        System.out.println(queue.size());

    }
}

class BoundedBlockingQueue {

    AtomicInteger size = new AtomicInteger(0);

    //队列长度
    private volatile int capacity;
    //队列容器
    private LinkedList<Integer> container;

    private static ReentrantLock lock = new ReentrantLock();
    Condition producer = lock.newCondition();
    Condition consumer = lock.newCondition();


    public BoundedBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        container = new LinkedList<Integer>();
    }

    /**
     * 入队
     *
     * @param element
     * @throws InterruptedException
     */
    public void enqueue(int element) throws InterruptedException {
        //每一个入队线程都获得锁，如果条件不满足会阻塞
        lock.lock();
        try {
            //当队列大于等于最大长度时，当前线程循环阻塞，当该线程获取cpu片段时 能够执行
            while (size.get() >= capacity) {
                producer.await();
            }
            //头部插入
            container.addFirst(element);
            //长度+1
            size.incrementAndGet();

            //通知出队线程
            consumer.signal();

        } finally {
            lock.unlock();
        }
    }

    /**
     * 出队
     * @return
     * @throws InterruptedException
     */
    public int dequeue() throws InterruptedException {
        //每一个出队线程都获取锁，如果条件不满足会阻塞
        lock.lock();
        try{
            //当队列为空时阻塞
            while (size.get() == 0){
                consumer.await();
            }
            //队尾出
            int lastValue = container.getLast();
            container.removeLast();

            //长度-1
            size.decrementAndGet();
            //通知入队线程
            producer.signal();

            //返回移除值
            return lastValue;
        }finally {
            lock.unlock();
        }
    }

    public int size() {
        //返回队列长度
        return size.get();
    }
}
