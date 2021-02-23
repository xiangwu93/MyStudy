package com.std.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chenxiangwu
 * @title: DefaultThreadPool
 * @projectName ThreadDemo
 * @date 2020/12/21 17:10
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job>{

    //最大工作线程数
    private static final  int MAX_WORKERS_NUMBERS = 10;
    //默认工作线程数
    private static final int DEFAULT_WORKER_NUMBERS = 5;
    //最小工作线程数
    private static final int MIN_WORKER_NUMBERS = 1;
    //工作等待列表
    private final LinkedList<Job> jobs = new LinkedList();
    //工作进行列表
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
    //工作者线程数量
    private int workerNum = DEFAULT_WORKER_NUMBERS;
    //线程编号
    private AtomicLong threadNum = new AtomicLong();

    public void execute(Job job) {

    }

    public void shutdown() {

    }

    public void addWorkers(int nums) {

    }

    public void removeWorkers(int nums) {

    }

    public int getJobSize() {
        return 0;
    }

    class Worker implements Runnable{

        public void run() {

        }
    }
}
