package com.neonex.payment.service.domain.ports.output.message.publisher;

import com.neonex.outbox.OutboxStatus;
import com.neonex.payment.service.domain.outbox.model.OrderOutboxMessage;

import java.util.function.BiConsumer;

public interface PaymentResponseMessagePublisher {
    void publish(OrderOutboxMessage orderOutboxMessage,
                 BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback);
}
