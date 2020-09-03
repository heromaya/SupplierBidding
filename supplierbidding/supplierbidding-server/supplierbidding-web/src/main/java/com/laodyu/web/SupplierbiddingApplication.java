package com.laodyu.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.activiti.spring.boot.SecurityAutoConfiguration.class})
@EntityScan("com.laodyu.entity")
@ComponentScan(basePackages = {"com.laodyu.service", "com.laodyu.web","com.laodyu.commons"})
@EnableJpaRepositories(basePackages = {"com.laodyu.dao"})
public class SupplierbiddingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupplierbiddingApplication.class, args);
    }

}
