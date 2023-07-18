package com.neonex.domain.port.output.message.publisher.restaurant;

import com.neonex.domain.entity.Order;
import com.neonex.domain.event.OrderPaidEvent;
import com.neonex.domain.event.publisher.DomainEventPublisher;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent, Order> {
}
