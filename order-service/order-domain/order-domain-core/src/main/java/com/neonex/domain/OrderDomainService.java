package com.neonex.domain;

import com.neonex.domain.entity.Order;
import com.neonex.domain.entity.Restaurant;
import com.neonex.domain.event.OrderCancelledEvent;
import com.neonex.domain.event.OrderCreatedEvent;
import com.neonex.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

    OrderPaidEvent payOrder(Order order);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
