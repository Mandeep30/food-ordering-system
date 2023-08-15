package com.neonex.payment.service.domain.mapper;

import com.neonex.domain.valueobject.CustomerId;
import com.neonex.domain.valueobject.Money;
import com.neonex.domain.valueobject.OrderId;
import com.neonex.payment.service.domain.dto.PaymentRequest;
import com.neonex.payment.service.domain.entity.Payment;
import com.neonex.payment.service.domain.event.PaymentEvent;
import com.neonex.payment.service.domain.outbox.model.OrderEventPayload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentDataMapper {

    public Payment paymentRequestModelToPayment(PaymentRequest paymentRequest) {
        return Payment.Builder.builder()
                .orderId(new OrderId(UUID.fromString(paymentRequest.getOrderId())))
                .customerId(new CustomerId(UUID.fromString(paymentRequest.getCustomerId())))
                .price(new Money(paymentRequest.getPrice()))
                .build();
    }

    public OrderEventPayload paymentEventToOrderEventPayload(PaymentEvent paymentEvent) {
        return OrderEventPayload.builder()
                .paymentId(paymentEvent.getPayment().getId().id().toString())
                .customerId(paymentEvent.getPayment().getCustomerId().id().toString())
                .orderId(paymentEvent.getPayment().getOrderId().id().toString())
                .price(paymentEvent.getPayment().getPrice().amount())
                .createdAt(paymentEvent.getCreatedAt())
                .paymentStatus(paymentEvent.getPayment().getPaymentStatus().name())
                .failureMessages(paymentEvent.getFailureMessages())
                .build();
    }
}
