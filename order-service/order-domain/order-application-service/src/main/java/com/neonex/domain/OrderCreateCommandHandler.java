package com.neonex.domain;

import com.neonex.domain.dto.create.CreateOrderCommand;
import com.neonex.domain.dto.create.CreateOrderResponse;
import com.neonex.domain.mapper.OrderDataMapper;
import com.neonex.domain.outbox.scheduler.payment.PaymentOutboxHelper;
import com.neonex.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Slf4j
class OrderCreateCommandHandler {


    private final OrderCreateHelper orderCreateHelper;
    private final OrderDataMapper orderDataMapper;
    private final OrderSagaHelper orderSagaHelper;
    private final PaymentOutboxHelper paymentOutboxHelper;

    public OrderCreateCommandHandler(OrderCreateHelper orderCreateHelper,
                                     OrderDataMapper orderDataMapper, OrderSagaHelper orderSagaHelper, PaymentOutboxHelper paymentOutboxHelper) {
        this.orderCreateHelper = orderCreateHelper;
        this.orderDataMapper = orderDataMapper;
        this.orderSagaHelper = orderSagaHelper;
        this.paymentOutboxHelper = paymentOutboxHelper;
    }

    /*
    this method needs to be public and should be called from another spring-bean to be transactional
    the transaction is committed when it jumps out the method marked @Transactional
     */
    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        //persist the order
        var orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
        log.info("Order is created with id " + orderCreatedEvent.getOrder().getId().id());

        CreateOrderResponse createOrderResponse = orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder(),
                "Order created successfully");
        //persist to payment outbox table so that poller can pick and publish the events
        paymentOutboxHelper.savePaymentOutboxMessage(orderDataMapper
                        .orderCreatedEventToOrderPaymentEventPayload(orderCreatedEvent),
                orderCreatedEvent.getOrder().getOrderStatus(),
                orderSagaHelper.orderStatusToSagaStatus(orderCreatedEvent.getOrder().getOrderStatus()),
                OutboxStatus.STARTED,
                UUID.randomUUID());

        log.info("Returning CreateOrderResponse with order id: {}", orderCreatedEvent.getOrder().getId());

        return createOrderResponse;
    }
}
