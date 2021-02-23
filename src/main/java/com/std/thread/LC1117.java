package com.std.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenxiangwu
 * @title: LC1117
 * @projectName ThreadDemo
 * @date 2020/12/31 14:46
 * <p>
 * 现在有两种线程，氧 oxygen 和氢 hydrogen，你的目标是组织这两种线程来产生水分子。
 * <p>
 * 存在一个屏障（barrier）使得每个线程必须等候直到一个完整水分子能够被产生出来。
 * <p>
 * 氢和氧线程会被分别给予 releaseHydrogen 和 releaseOxygen 方法来允许它们突破屏障。
 * <p>
 * 这些线程应该三三成组突破屏障并能立即组合产生一个水分子。
 * <p>
 * 你必须保证产生一个水分子所需线程的结合必须发生在下一个水分子产生之前。
 * <p>
 * 换句话说:
 * <p>
 * 如果一个氧线程到达屏障时没有氢线程到达，它必须等候直到两个氢线程到达。
 * 如果一个氢线程到达屏障时没有其它线程到达，它必须等候直到一个氧线程和另一个氢线程到达。
 * 书写满足这些限制条件的氢、氧线程同步代码。
 */
public class LC1117 {

    private Semaphore semaH = new Semaphore(2);
    private Semaphore semaO = new Semaphore(1);

    private AtomicInteger groupCount = new AtomicInteger(0);

    private static final int GROUP_H_LIMIT = 2;
    private static final int GROUP_O_LIMIT = 1;
    private static final int GROUP_TOTAL_LIMIT = GROUP_O_LIMIT + GROUP_H_LIMIT;


    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        this.semaH.acquire(1);
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        this.groupCount.incrementAndGet();
        reset();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        this.semaO.acquire(1);
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        this.groupCount.incrementAndGet();
        reset();
    }

    private void reset(){
        if (this.groupCount.compareAndSet(GROUP_TOTAL_LIMIT, 0)){
            this.semaH.release(GROUP_H_LIMIT);
            this.semaO.release(GROUP_O_LIMIT);
        }
    }
}
