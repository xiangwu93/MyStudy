package com.std.thread;

import java.util.PriorityQueue;

/**
 * @author chenxiangwu
 * @title: LC1046
 * @projectName ThreadDemo
 * @date 2020/12/30 16:07
 * 有一堆石头，每块石头的重量都是正整数。
 *
 * 每一回合，从中选出两块 最重的 石头，然后将它们一起粉碎。假设石头的重量分别为x 和y，且x <= y。那么粉碎的可能结果如下：
 *
 * 如果x == y，那么两块石头都会被完全粉碎；
 * 如果x != y，那么重量为x的石头将会完全粉碎，而重量为y的石头新重量为y-x。
 * 最后，最多只会剩下一块石头。返回此石头的重量。如果没有石头剩下，就返回 0。
 *
 * 示例：
 *
 * 输入：[2,7,4,1,8,1]
 * 输出：1
 * 解释：
 * 先选出 7 和 8，得到 1，所以数组转换为 [2,4,1,1,1]，
 * 再选出 2 和 4，得到 2，所以数组转换为 [2,1,1,1]，
 * 接着是 2 和 1，得到 1，所以数组转换为 [1,1,1]，
 * 最后选出 1 和 1，得到 0，最终数组转换为 [1]，这就是最后剩下那块石头的重量。
 *
 * 提示：
 *
 * 1 <= stones.length <= 30
 * 1 <= stones[i] <= 1000
 *
 */
public class LC1046 {

    public static void main(String[] args) {
        int[] a = {2,7,4,1,8,1};
        System.out.println(lastStoneWeight(a));
    }

    public static int lastStoneWeight(int[] stones){

        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b)->b-a);
        for(int stone: stones){
            pq.offer(stone);
        }
        int distant = 0;
        while (pq.size() > 1){
            if ((distant = pq.poll() - pq.poll()) > 0){
                pq.offer(distant);
            }
        }
        return pq.isEmpty() ? 0:pq.poll();
    }
}
