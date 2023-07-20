package com.jobdata.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan(basePackages = "com.jobdata")
public class SpringConfiguration {
    public static void main(String[] args) {
        SpringApplication.run(SpringConfiguration.class, args);
    }
}