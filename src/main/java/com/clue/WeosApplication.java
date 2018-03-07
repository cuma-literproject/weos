package com.clue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class WeosApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(WeosApplication.class, args);
    }
}