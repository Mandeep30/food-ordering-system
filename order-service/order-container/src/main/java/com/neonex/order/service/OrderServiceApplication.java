package com.neonex.order.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.neonex.dataaccess", "com.neonex.common.dataaccess"})
@EntityScan(basePackages = {"com.neonex.dataaccess", "com.neonex.common.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.neonex")
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
