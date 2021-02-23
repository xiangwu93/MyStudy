package com.std.thread;

import java.util.HashMap;

/**
 * @author chenxiangwu
 * @title: LC1
 * @projectName ThreadDemo
 * @date 2021/2/22 10:32
 */
public class LC1 {

    public static void main(String[] args) {
        int nums[] = {2,7,11,15};
        int target = 13;
        System.out.println(twoSum(nums, target));
    }

    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap();
        for (int i = 0; i< nums.length; i++){
            if (map.containsKey(target - nums[i])){
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return new int[0];
    }
}

