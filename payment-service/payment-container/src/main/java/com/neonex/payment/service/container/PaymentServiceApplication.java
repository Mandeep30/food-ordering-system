package com.neonex.payment.service.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.neonex.payment.service.dataaccess")
@EntityScan(basePackages = "com.neonex.payment.service.dataaccess")
@SpringBootApplication(scanBasePackages = "com.neonex")
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
