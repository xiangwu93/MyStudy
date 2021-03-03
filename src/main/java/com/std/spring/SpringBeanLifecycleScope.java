package com.std.spring;

/**
 * @author chenxiangwu
 * @title: SpringBeanLifecycleScope
 * @projectName ThreadDemo
 * @date 2021/3/2 18:39
 * @Component和@Bean都是用来定义bean并讲bean添加到ioc容器，但是两者在使用上又有不同
 * @Component:一般是注解在类上面，并且可以在括号里注明这个bean的一个别名
 * @Bean：一般是注解在返回一个实体对象的方法上，同样也可以为这个bean起别名
 *
 * 作用域（scope）：
 * 在spring boot中，我们通常用@scope（" "）这个注解来指定bean的作用域，scope有以下几种取值：
 *   singleton(默认值)：单例对象，这个对象在容器中只创建一次
 *   prototype:多例原型，被标识为多例的对象，每次再获取才会创建，每次创建都是新的对象
 *   request：web环境下，对象与request生命周期一样
 *   session：web环境下，对象与session生命周期一样，不同session使用不同bean
 *   globalSession：与seesion不同的是，所有的session使用同一个bean
 * 生命周期：
 *
 *
 */
public interface SpringBeanLifecycleScope {
}
