package com.std.thread;

import java.util.*;

/**
 * @author chenxiangwu
 * @title: Demo2
 * @projectName ThreadDemo
 * @date 2020/12/16 10:50
 */
public class Demo2 {
    private void getEnumCount(List<String> list){
        HashMap<String, Integer> maps = new HashMap<String, Integer>();
        for(String str : list){
            if (maps.containsKey(str)){
                maps.put(str, maps.get(str) + 1);
            }else {
                maps.put(str, 1);
            }
        }
        List<Map.Entry<String, Integer>> result = new ArrayList<Map.Entry<String, Integer>>();
        Collections.sort(result, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        System.out.println("result :" + result);
    }
}
