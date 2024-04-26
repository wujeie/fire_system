package com.wzs.fire;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wzs.fire.mapper")
public class FireApplication {

    public static void main(String[] args) {
        SpringApplication.run(FireApplication.class, args);
    }

}
