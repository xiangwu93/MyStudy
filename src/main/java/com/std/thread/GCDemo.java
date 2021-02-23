package com.std.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxiangwu
 * @title: GCDemo
 * @projectName ThreadDemo
 * @date 2021/1/27 17:11
 */
public class GCDemo {

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            List<String> list = new ArrayList<>();
            list.add("aaaaaaaaaaaaa");
        }
        System.gc();
    }

}
