package com.endava.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.endava.demo")
@PropertySource("classpath:application-test.properties")
public class TestContext {
}
