package com.neonex.domain;

import com.neonex.domain.dto.message.PaymentResponse;
import com.neonex.domain.event.OrderPaidEvent;
import com.neonex.domain.port.input.message.listener.payment.PaymentResponseMessageListener;
import com.neonex.domain.port.output.message.publisher.restaurant.OrderPaidRestaurantRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Slf4j
class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {
    private final OrderPaidRestaurantRequestMessagePublisher restaurantRequestMessagePublisher;
    private final OrderPaymentSaga orderPaymentSaga;

    PaymentResponseMessageListenerImpl(OrderPaidRestaurantRequestMessagePublisher restaurantRequestMessagePublisher,
                                       OrderPaymentSaga orderPaymentSaga) {
        this.restaurantRequestMessagePublisher = restaurantRequestMessagePublisher;
        this.orderPaymentSaga = orderPaymentSaga;
    }

    @Override
    public void paymentCompleted(PaymentResponse paymentResponse) {
        OrderPaidEvent domainEvent = orderPaymentSaga.process(paymentResponse);
        log.info("Publishing OrderPaidEvent for order id: {}", paymentResponse.getOrderId());
        restaurantRequestMessagePublisher.publish(domainEvent);
    }

    @Override
    public void paymentCancelled(PaymentResponse paymentResponse) {
        orderPaymentSaga.rollback(paymentResponse);
        log.info("Order is roll backed for order id: {} with failure messages: {}",
                paymentResponse.getOrderId(),
                String.join(",", paymentResponse.getFailureMessages()));
    }
}
