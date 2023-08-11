package com.neonex.restaurant.service.domain.ports.output.message.publisher;

import com.neonex.domain.event.publisher.DomainEventPublisher;
import com.neonex.restaurant.service.domain.entity.OrderApproval;
import com.neonex.restaurant.service.domain.event.OrderRejectedEvent;

public interface OrderRejectedMessagePublisher extends DomainEventPublisher<OrderRejectedEvent, OrderApproval> {
}
