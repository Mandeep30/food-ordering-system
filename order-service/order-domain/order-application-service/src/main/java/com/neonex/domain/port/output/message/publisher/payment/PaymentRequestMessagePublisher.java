package com.neonex.domain.port.output.message.publisher.payment;

import com.neonex.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import com.neonex.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface PaymentRequestMessagePublisher {

    void publish(OrderPaymentOutboxMessage orderPaymentOutboxMessage,
                 BiConsumer<OrderPaymentOutboxMessage, OutboxStatus> outboxCallback);
}
