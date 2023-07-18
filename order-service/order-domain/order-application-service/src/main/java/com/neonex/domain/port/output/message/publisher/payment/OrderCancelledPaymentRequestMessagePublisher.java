package com.neonex.domain.port.output.message.publisher.payment;

import com.neonex.domain.entity.Order;
import com.neonex.domain.event.OrderCancelledEvent;
import com.neonex.domain.event.OrderCreatedEvent;
import com.neonex.domain.event.publisher.DomainEventPublisher;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent, Order> {
}
