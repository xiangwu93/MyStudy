package com.std.designmode;

import java.io.Serializable;

/**
 * @author chenxiangwu
 * @title: LazySingleton
 * @projectName ThreadDemo
 * @date 2021/3/2 15:23
 * <p>
 * 1.私有构造器：
 * 将本类的构造器私有化，其实这是单例的一个非常重要的步骤，没有这个步骤，可以说你的就不是单例模式。这个步骤其实是防止外部函数
 * 在new的时候能构造出来新的对象，我们说单例要保证一个类只有一个实例，如果外部能new新的对象，那我们单例就是失败的。所以无论什么
 * 时候一定要将这个构造器私有化
 * 2.单例模式中的线程安全+延时加载（懒汉模式）
 * 2.1原始版本
 * 2.2加锁
 * 2.3静态内部类
 * 3.序列化与反序列化安全
 * 饿汉模式：饿汉式就是在类的初始化阶段就已经加载好了，就算你不用这个对象，这个对象也已经创建好，不像懒汉式要等到要用的时候才
 * 加载。这是两种模式的一个很大的区别，事实上饿汉式是线程安全的，就像懒汉式的内部类加载一样，是由JVM加的锁，但是两者都不一定是
 * 序列化安全的。上面的饿汉式是序列化安全的，为什么？因为多加了readResolve()方法。这时候有人会问为什么要在饿汉式上多加一个这个
 * 方法。这里的源码我就不一一解析了。事实上在反序列化（从文件中读取类）的时候，底层会有一个判断。如果这个类在运行时是可序列化的，
 * 那么我就会在读取的时候创建一个新的类（反射创建），否则我就会让这个类为空。再后面又有一个判断，如果我的类这时候不为空，我就会
 * 通过反射尝试调用readResolve()方法，然后最终返回给我的ObjectInputStream流。没有的话我就返回之前创建的新对象。所以这就相当于
 * 覆盖了之前读取时候创建的类。
 * 4.防止反射攻击
 *   在私有构造器中加入一个空判断来抛出异常，反射攻击的时候，上面的懒汉式中的内部类代码和饿汉式中的序列化安全代码都是可以防御反
 * 射攻击。
 *   枚举对象不能被反射创建，并且序列化与反序列化中枚举类型不会被创建出新的，推荐枚举类型来完成单例模式。
 *   常用的Spring框架，简单说一下就是Spring中对象创建在Bean作用域中仅创建一个，和我们上面讲的单例还是有稍许区别，这个单例的作用域
 * 是整个应用的上下文，通俗一点理解就是Spring就像一个商店，里面的商品一种只有一个，大家看见的一个商品都是同一个，这一种商品中不
 * 会再有另一个商品了。
 */

//2.1原始
public class LazySingleton {
    private static LazySingleton lazySingleton = null;

    private LazySingleton() {
        if (lazySingleton != null) {
            throw new RuntimeException("单例模式构造器禁止反射调用");
        }
    }

    public static LazySingleton getInstance() {
        if (lazySingleton == null) {
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }
}

//2.2加锁
class LazyDoubleCheckingSingleton {
    private volatile static LazyDoubleCheckingSingleton lazyDoubleCheckingSingleton = null;

    private LazyDoubleCheckingSingleton() {
    }

    public static LazyDoubleCheckingSingleton getInstance() {
        if (lazyDoubleCheckingSingleton == null) {
            synchronized (LazyDoubleCheckingSingleton.class) {
                if (lazyDoubleCheckingSingleton == null) {
                    lazyDoubleCheckingSingleton = new LazyDoubleCheckingSingleton();
                }
            }
        }
        return lazyDoubleCheckingSingleton;
    }
}

//2.3静态内部类
class StaticInnerClassSingleton {
    private static class InnerClass {
        private static StaticInnerClassSingleton staticInnerClassSingleton = new StaticInnerClassSingleton();
    }

    public static StaticInnerClassSingleton getInstance() {
        return InnerClass.staticInnerClassSingleton;
    }

    private StaticInnerClassSingleton() {
        if (InnerClass.staticInnerClassSingleton != null) {
            throw new RuntimeException("单例构造器禁止反射调用");
        }
    }
}

class HungrySingleton implements Serializable {
    private static final HungrySingleton hungrySingleton;

    static {
        hungrySingleton = new HungrySingleton();
    }

    private HungrySingleton() {
        if (hungrySingleton != null) {
            throw new RuntimeException("单例构造器禁止反射调用");
        }
    }

    public static HungrySingleton getInstance() {
        return hungrySingleton;
    }

    private Object readResolve() {
        return hungrySingleton;
    }
}

//枚举
enum EnumInstance {
    INSTANCE;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static EnumInstance getInstance(){
        return INSTANCE;
    }
}
