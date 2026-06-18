package com.lightblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lightblog.mapper")
public class LightBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LightBlogApplication.class, args);
    }
}
