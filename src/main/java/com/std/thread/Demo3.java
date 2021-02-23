package com.std.thread;


/**
 * @author chenxiangwu
 * @title: Demo3
 * @projectName ThreadDemo
 * @date 2020/12/16 11:01
 */
public class Demo3 {
    private static String strReverse(String str){
        char[] chars = str.toCharArray();
        int low = 0;
        int high = chars.length-1;
        while (low < high){
            char t ;
            if (low == 0 && Character.isUpperCase(chars[low])){
                t = Character.toLowerCase(chars[low]);
                chars[low] = Character.toUpperCase(chars[high]);
            }else {
                t = chars[low];
                chars[low] = chars[high];
            }
            chars[high] = t;
            low ++;
            high --;
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        String str = "Today is a Nice day";
        String[] strs = str.split(" ");
        for (int i = 0; i < strs.length; i++) {
            System.out.print(strReverse(strs[i]) + " ");
        }
    }
}
