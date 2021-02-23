package com.std.thread;

import java.util.concurrent.*;

/**
 * @author chenxiangwu
 * @title: CreateThread
 * @projectName ThreadDemo
 * @date 2020/12/25 20:54
 */
public class CreateThread {

    public static void main(String[] args) {
        Thread thread1 = new Thread1();
        thread1.start();
        Thread thread2 = new Thread(new Thread2());
        thread2.start();
        Future<String> future = new FutureTask<String>((Callable<String>)() ->{
            return Thread.currentThread().getName();
        });
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    ExecutorService executor1 = Executors.newSingleThreadExecutor();
    ExecutorService executor2 = Executors.newCachedThreadPool();
    ExecutorService executor3 = Executors.newFixedThreadPool(5);
    ExecutorService executor4 = Executors.newScheduledThreadPool(5);

    LinkedBlockingDeque<Runnable> linkedBlockingDeque = new LinkedBlockingDeque<Runnable>(20);
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5, 50, TimeUnit.MILLISECONDS, linkedBlockingDeque);

}

class Thread1 extends Thread{
    @Override
    public void run() {
        System.out.println("Thread1: " + this.getName());
    }
}

class Thread2 implements Runnable{
    public void run() {
        System.out.println("Thread2: " + Thread.currentThread().getName());
    }
}

class Thread3 implements Callable{

    public Object call() throws Exception {
        return Thread.currentThread().getName();
    }
}
