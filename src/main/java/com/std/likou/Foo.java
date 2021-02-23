package com.std.likou;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenxiangwu
 * @title: Foo
 * @projectName ThreadDemo
 * @date 2020/12/8 19:12
 */
public class Foo {


    int num;
    Lock lock;
    Condition condition1;Condition condition2;Condition condition3;
    public Foo() {
        num = 1;
        lock = new ReentrantLock();
        condition1 = lock.newCondition();
        condition2 = lock.newCondition();
        condition3 = lock.newCondition();
    }

    public static void main(String[] args) {

    }

    public void first(Runnable printFirst) throws InterruptedException {

        lock.lock();
        try {
            while (num != 1) {
                condition1.await();
            }
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            num = 2;
            condition2.signal();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public void second(Runnable printSecond) throws InterruptedException {
        lock.lock();
        try{
            while (num !=2){
                condition2.await();
            }
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            num = 3;
            condition3.signal();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public void third(Runnable printThird) throws InterruptedException {
        lock.lock();
        try{
            while (num != 3){
                condition3.await();
            }
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
            num = 1;
            condition1.signal();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }


}
