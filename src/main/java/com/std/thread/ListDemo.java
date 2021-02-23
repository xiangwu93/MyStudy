package com.std.thread;

import java.util.*;

/**
 * @author chenxiangwu
 * @title: ListDemo
 * @projectName ThreadDemo
 * @date 2020/12/15 16:56
 */
public class ListDemo {

    public static void main(String[] args) {
        System.out.println(reverse("AvcccD"));
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("qweqwe");
        list.add("qweqwe");
        list.add("f");
        list.add("qweqwe");
        HashMap map = getMultipleEnum(list);

    }

    private static HashMap<String, Integer> getMultipleEnum(List<String> list){
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        for (String str:list){
            if (hashMap.containsKey(str)){
                hashMap.put(str, hashMap.get(str).intValue() +1);
            }else {
                hashMap.put(str, 1);
            }
        }


        return hashMap;
    }

    private static String reverse(String str){
        return new StringBuffer(str).reverse().toString();
    }
}
