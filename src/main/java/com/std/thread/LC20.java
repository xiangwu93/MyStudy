package com.std.thread;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author chenxiangwu
 * @title: LC20
 * @projectName ThreadDemo
 * @date 2021/2/22 10:45
 */
public class LC20 {

    public static void main(String[] args) {
        String s = "())";
        System.out.println(isValid(s));
    }

    public static boolean isValid(String s) {
        int n = s.length();
        if (n % 2 == 1) {
            return false;
        }

        Map<String, String> pairs = new HashMap<>();
        pairs.put("}","{");
        pairs.put(")","(");
        pairs.put("]","[");
        LinkedList stack = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            String a = String.valueOf(s.charAt(i));
            if (pairs.containsKey(a)) {
                if (stack.isEmpty() || !stack.peek().equals(pairs.get(a))) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(a);
            }
        }
        return stack.isEmpty();
    }
}
