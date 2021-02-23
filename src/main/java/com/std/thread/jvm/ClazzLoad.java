package com.std.thread.jvm;

/**
 * @author chenxiangwu
 * @title: ClazzLoad
 * @projectName ThreadDemo
 * @date 2021/1/28 14:13
 */
public class ClazzLoad {

    public static void main(String[] args) {
//          int x = Dson.count;
//          Dson dson = new Dson();
            Dson[] dsons = new Dson[10];

    }


}

class Dgrandpa{
    static {
        System.out.println("Initialize class Dgrandpa");
    }
}

class Dfather extends Dgrandpa {
    static int count = 1;
    static {
        System.out.println("Initialize class Dfather");
    }
}

class Dson extends Dfather {
    static {
        System.out.println("Initialize class Dson");
    }
}
