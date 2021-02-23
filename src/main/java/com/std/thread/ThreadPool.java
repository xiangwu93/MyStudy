package com.std.thread;

/**
 * @author chenxiangwu
 * @title: ThreadPool
 * @projectName ThreadDemo
 * @date 2020/12/21 17:06
 */
public interface ThreadPool<Job extends Runnable> {

    //执行job job需要实现Runnable
    void execute(Job job);
    //关闭线程池
    void shutdown();
    //增加工作者线程
    void addWorkers(int nums);
    //减少工作者线程
    void removeWorkers(int nums);
    //得到正在等待执行的任务数量
    int getJobSize();
}
