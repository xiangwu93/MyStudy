package com.std.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author chenxiangwu
 * @title: DemoApplication
 * @projectName ThreadDemo
 * @date 2021/3/9 15:44
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.std"})
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
