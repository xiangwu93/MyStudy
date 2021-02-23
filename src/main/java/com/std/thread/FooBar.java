package com.std.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenxiangwu
 * @title: FooBar
 * @projectName ThreadDemo
 * @date 2020/12/9 19:15
 */
class test{
    public static void main(String[] args) {

    }
}
class FooBar {

    private int n;
    private ReentrantLock lock = new ReentrantLock();
    Condition foo = lock.newCondition();
    Condition bar = lock.newCondition();
    int count = 1;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        lock.lock();
        try {
            if (count != 1) {
                foo.await();
            }

            for (int i = 0; i < n; i++) {

                // printFoo.run() outputs "foo". Do not change or remove this line.
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
            bar.signal();
            count = 2;
        } finally {
            lock.unlock();
        }

    }

    public void bar(Runnable printBar) throws InterruptedException {
        lock.lock();
        try{
            for (int i = 0; i < n; i++) {

                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
            }
        }finally {
            lock.unlock();
        }

    }


}
