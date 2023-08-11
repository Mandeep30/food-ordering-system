package com.neonex.payment.service.messaging.mapper;

import com.neonex.kafka.order.avro.model.PaymentOrderStatus;
import com.neonex.kafka.order.avro.model.PaymentRequestAvroModel;
import com.neonex.kafka.order.avro.model.PaymentResponseAvroModel;
import com.neonex.kafka.order.avro.model.PaymentStatus;
import com.neonex.payment.service.domain.dto.PaymentRequest;
import com.neonex.payment.service.domain.event.PaymentCancelledEvent;
import com.neonex.payment.service.domain.event.PaymentCompletedEvent;
import com.neonex.payment.service.domain.event.PaymentFailedEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class PaymentMessagingDataMapper {


    public PaymentResponseAvroModel
    paymentCompletedEventToPaymentResponseAvroModel(PaymentCompletedEvent paymentCompletedEvent) {
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setPaymentId(paymentCompletedEvent.getPayment().getId().id().toString())
                .setCustomerId(paymentCompletedEvent.getPayment().getCustomerId().id().toString())
                .setOrderId(paymentCompletedEvent.getPayment().getOrderId().id().toString())
                .setPrice(paymentCompletedEvent.getPayment().getPrice().amount())
                .setCreatedAt(paymentCompletedEvent.getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(paymentCompletedEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(paymentCompletedEvent.getFailureMessages())
                .build();
    }

    public PaymentResponseAvroModel
    paymentCancelledEventToPaymentResponseAvroModel(PaymentCancelledEvent paymentCancelledEvent) {
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setPaymentId(paymentCancelledEvent.getPayment().getId().id().toString())
                .setCustomerId(paymentCancelledEvent.getPayment().getCustomerId().id().toString())
                .setOrderId(paymentCancelledEvent.getPayment().getOrderId().id().toString())
                .setPrice(paymentCancelledEvent.getPayment().getPrice().amount())
                .setCreatedAt(paymentCancelledEvent.getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(paymentCancelledEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(paymentCancelledEvent.getFailureMessages())
                .build();
    }

    public PaymentResponseAvroModel
    paymentFailedEventToPaymentResponseAvroModel(PaymentFailedEvent paymentFailedEvent) {
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setPaymentId(paymentFailedEvent.getPayment().getId().id().toString())
                .setCustomerId(paymentFailedEvent.getPayment().getCustomerId().id().toString())
                .setOrderId(paymentFailedEvent.getPayment().getOrderId().id().toString())
                .setPrice(paymentFailedEvent.getPayment().getPrice().amount())
                .setCreatedAt(paymentFailedEvent.getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(paymentFailedEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(paymentFailedEvent.getFailureMessages())
                .build();
    }

    public PaymentRequest paymentRequestAvroModelToPaymentRequest(PaymentRequestAvroModel paymentRequestAvroModel) {
        return PaymentRequest.builder()
                .id(paymentRequestAvroModel.getId())
                .sagaId(paymentRequestAvroModel.getSagaId())
                .customerId(paymentRequestAvroModel.getCustomerId())
                .orderId(paymentRequestAvroModel.getOrderId())
                .price(paymentRequestAvroModel.getPrice())
                .createdAt(paymentRequestAvroModel.getCreatedAt())
                .paymentOrderStatus(com.neonex.domain.valueobject.PaymentOrderStatus.valueOf
                        (paymentRequestAvroModel.getPaymentOrderStatus().name()))
                .build();
    }
}
