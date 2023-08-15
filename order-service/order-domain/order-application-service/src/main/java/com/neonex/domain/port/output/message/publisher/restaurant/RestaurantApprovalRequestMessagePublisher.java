package com.neonex.domain.port.output.message.publisher.restaurant;

import com.neonex.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import com.neonex.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface RestaurantApprovalRequestMessagePublisher {

    void publish(OrderApprovalOutboxMessage orderApprovalOutboxMessage,
                 BiConsumer<OrderApprovalOutboxMessage, OutboxStatus> outboxCallback);
}
