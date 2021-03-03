package com.std.spring;

/**
 * @author chenxiangwu
 * @title: SpringIOC
 * @projectName ThreadDemo
 * @date 2021/3/2 18:44
 *
 * IOC：控制反转，一种设计思想，它是Spring的核心。简单点说就是spring管理bean的容器。IOC容器一般具备两个基本功能：
 * 　　1、通过描述管理Bean，包括发布和获取。
 * 　　2、描述Bean之间的依赖关系。这两个问题深究下去是没有边界的，尤其是Bean之间的依赖关系，这个就是spring的核心。
 *    参考SpringBean简要流程.png
 *
 * 从图片可以看到Spring Bean 在整个SpringBoot 项目中至关重要，它经过的路径如下：
 *    1.实例化【IOC容器寻找Bean的定义信息并将其实例化】
 *    2.设置bean的Aware【Aware意指能提前感知的,是spring的一个重要接口,使用依赖注入，spring按照Bean定义信息配置Bean的所有属性】
 *    3.BeanPostProcessor.postProcessBeforeInitialization(Object bean, String beanName)【如果BeanPostProcessor和Bean关联，
 * 那么其postProcessBeforeInitialization()方法将被调用，Spring 框架会遍历得到容器中所有的 BeanPostProcessor ，挨个执行】
 *    4.InitializingBean.afterPorpertiesSet【初始化bean, springboot读取properties文件的过程，默认的application.properties
 * 还有其他方式】
 *    5.BeanPostProcessor.postProcessAfterInitialization(Object bean, String beanName)【如果有BeanPostProcessor和Bean关联,
 * 那么其postProcessAfterInitialization()方法将被调用】
 *    6.SmartInitializingSingleton.afterSingletonsInstantiated
 *    7.SmartLifecycle.start
 *    8.运行Bean
 *    9.SmartLifecycle.stop(Runnable callback)
 *    10.DisposableBean.destroy()【销毁】
 */
public interface SpringIOC {
}
