package com.std.algorithm;

/**
 * @author chenxiangwu
 * @title: QuickSort
 * @projectName ThreadDemo
 * @date 2020/12/15 11:43
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] a = { 6, 4, 3, 1, 2, 6,7, 5, 8, 9, 10};
        quick(a);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i] + "");
        }
    }

    private static void quick(int[] a) {
        if (a.length > 0) {
            quickSort(a, 0, a.length - 1);
        }
    }

    private static void quickSort(int[] a, int left, int right) {
        if (left < right) {
            int temp = getTemp(a, left, right);
            quickSort(a, 0, temp - 1);
            quickSort(a, temp + 1, right);
        }
    }

    private static int getTemp(int[] a, int left, int right) {
        int temp = a[left];
        while (left < right) {
            //从右向左 找到比基准元素小的元素位置
            while (left < right && a[right] >= temp) {
                right--;
            }
            a[left] = a[right];
            //从做向右 找到比基准元素大的元素位置
            while (left < right && a[left] <= temp) {
                left++;
            }
            a[right] = a[left];
        }
        a[left] = temp;
        return left;
    }
}
