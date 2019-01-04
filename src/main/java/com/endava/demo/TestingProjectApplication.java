package com.endava.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableHystrix
@SpringBootApplication
public class TestingProjectApplication {

    private static final Logger LOGGER = LogManager.getLogger(TestingProjectApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TestingProjectApplication.class, args);
    }

}
