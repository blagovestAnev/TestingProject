package com.endava.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class TestingProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestingProjectApplication.class, args);
    }
}
