package com.std.jvm;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @author chenxiangwu
 * @title: LookMarkWord
 * @projectName ThreadDemo
 * @date 2021/2/25 16:35
 */
public class LookMarkWord {
    public static void main(String[] args) {
        Object md = new Object();
        for (int i=1;i<4;i++){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(ClassLayout.parseInstance(md).toPrintable());
                synchronized (md){
                    System.out.println(ClassLayout.parseInstance(md).toPrintable());
                    System.out.println(Thread.currentThread().getName());
                }
            }
        }).start();

        }
//        System.out.println(ClassLayout.parseInstance(md).toPrintable());
//        synchronized (md){
//            System.out.println(ClassLayout.parseInstance(md).toPrintable());
//        }
//        System.out.println(ClassLayout.parseInstance(md).toPrintable());
//        System.out.println(VM.current().details());


    }
}

