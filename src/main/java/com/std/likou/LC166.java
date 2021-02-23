package com.std.likou;

import java.util.HashMap;

/**
 * @author chenxiangwu
 * @title: LC166
 * @projectName ThreadDemo
 * @date 2021/2/22 16:41
 * 给定两个整数，分别表示分数的分子numerator 和分母 denominator，以 字符串形式返回小数 。
 *
 * 如果小数部分为循环小数，则将循环的部分括在括号内。
 *
 * 如果存在多个答案，只需返回 任意一个 。
 *
 * 对于所有给定的输入，保证 答案字符串的长度小于 10的4次方 。

 */
public class LC166 {
    public static void main(String[] args) {
        System.out.println(fractionToDecimal(2,3));
    }
    public static String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0){
            return "0";
        }

        StringBuffer res = new StringBuffer();

        if (numerator < 0 || denominator < 0){
            res.append("-");
        }

        long num = Math.abs(Long.valueOf(numerator));
        long den = Math.abs(Long.valueOf(denominator));

        res.append(num/den);

        long remainder = num % den;

        if (remainder == 0){
            return res.toString();
        }

        res.append(".");

        HashMap<Long, Integer> hashMap = new HashMap<>();
        while (remainder != 0){
            if (hashMap.containsKey(remainder)){
                res.insert(hashMap.get(remainder),"(");
                res.append(")");
                break;
            }
            hashMap.put(remainder, res.length());
            remainder = remainder * 10;
            res.append(remainder/denominator);
            remainder = remainder % den;
        }
        return res.toString();
    }
}
