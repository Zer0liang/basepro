package com.chechetimes.wzl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.chechetimes.wzl.mapper"})
public class WzlApplication {

    public static void main(String[] args) {
        SpringApplication.run(WzlApplication.class, args);
    }

}
