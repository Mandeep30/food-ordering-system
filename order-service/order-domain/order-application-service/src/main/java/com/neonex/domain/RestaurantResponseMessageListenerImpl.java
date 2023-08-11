package com.neonex.domain;

import com.neonex.domain.dto.message.RestaurantApprovalResponse;
import com.neonex.domain.event.OrderCancelledEvent;
import com.neonex.domain.port.input.message.listener.restaurant.RestaurantResponseMessageListener;
import com.neonex.domain.port.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Slf4j
public class RestaurantResponseMessageListenerImpl implements RestaurantResponseMessageListener {
    private final OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher;
    private final OrderApprovalSaga orderApprovalSaga;

    public RestaurantResponseMessageListenerImpl(OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher,
                                                 OrderApprovalSaga orderApprovalSaga) {
        this.orderCancelledPaymentRequestMessagePublisher = orderCancelledPaymentRequestMessagePublisher;
        this.orderApprovalSaga = orderApprovalSaga;
    }

    @Override
    public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {
        orderApprovalSaga.process(restaurantApprovalResponse);
        log.info("Order Approved for order id: {}", restaurantApprovalResponse.getOrderId());
    }

    @Override
    public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {
        OrderCancelledEvent orderCancelledEvent = orderApprovalSaga.rollback(restaurantApprovalResponse);
        log.info("Order rolled back for order id: {}", restaurantApprovalResponse.getOrderId());
        orderCancelledPaymentRequestMessagePublisher.publish(orderCancelledEvent);
        log.info("Order is roll backed for order id: {} with failure messages: {}",
                restaurantApprovalResponse.getOrderId(),
                String.join(",", restaurantApprovalResponse.getFailureMessages()));
    }
}
