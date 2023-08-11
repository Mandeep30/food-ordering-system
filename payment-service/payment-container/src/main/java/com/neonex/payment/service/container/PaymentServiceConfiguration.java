package com.neonex.payment.service.container;

import com.neonex.payment.service.PaymentDomainService;
import com.neonex.payment.service.PaymentDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentServiceConfiguration {
    @Bean
    public PaymentDomainService paymentDomainService() {
        return new PaymentDomainServiceImpl();

    }
}
