package com.neonex.domain.mapper;

import com.neonex.domain.dto.create.CreateOrderCommand;
import com.neonex.domain.dto.create.CreateOrderResponse;
import com.neonex.domain.dto.track.TrackOrderResponse;
import com.neonex.domain.entity.Order;
import com.neonex.domain.entity.OrderItem;
import com.neonex.domain.entity.Product;
import com.neonex.domain.entity.Restaurant;
import com.neonex.domain.event.OrderCancelledEvent;
import com.neonex.domain.event.OrderCreatedEvent;
import com.neonex.domain.event.OrderPaidEvent;
import com.neonex.domain.outbox.model.approval.OrderApprovalEventPayload;
import com.neonex.domain.outbox.model.approval.OrderApprovalEventProduct;
import com.neonex.domain.outbox.model.payment.OrderPaymentEventPayload;
import com.neonex.domain.valueobject.CustomerId;
import com.neonex.domain.valueobject.Money;
import com.neonex.domain.valueobject.PaymentOrderStatus;
import com.neonex.domain.valueobject.ProductId;
import com.neonex.domain.valueobject.RestaurantId;
import com.neonex.domain.valueobject.RestaurantOrderStatus;
import com.neonex.domain.valueobject.StreetAddress;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * DTO to domain objects and vice versa
 */
@Component
public class OrderDataMapper {
    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.Builder.builder()
                .id(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getItems().stream()
                        .map(item -> new Product(new ProductId(item.getProductId()))).toList())
                .build();

    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.Builder.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .items(createOrderCommand.getItems().stream().map(item -> OrderItem.Builder.builder()
                        .price(new Money(item.getPrice()))
                        .productId(new Product(new ProductId(item.getProductId())))
                        .quantity(item.getQuantity())
                        .subTotal(new Money(item.getSubTotal()))
                        .build()).toList())
                .price(new Money(createOrderCommand.getPrice()))
                .streetAddress(new StreetAddress(UUID.randomUUID(),
                        createOrderCommand.getAddress().getStreet(),
                        createOrderCommand.getAddress().getPostalCode(),
                        createOrderCommand.getAddress().getCity()))
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().id())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return TrackOrderResponse
                .builder()
                .orderTrackingId(order.getTrackingId().id())
                .orderStatus(order.getOrderStatus())
                .failureMessage(order.getFailureMessages())
                .build();
    }

    public OrderPaymentEventPayload orderCreatedEventToOrderPaymentEventPayload(OrderCreatedEvent orderCreatedEvent) {
        return OrderPaymentEventPayload.builder()
                .customerId(orderCreatedEvent.getOrder().getCustomerId().id().toString())
                .orderId(orderCreatedEvent.getOrder().getId().id().toString())
                .price(orderCreatedEvent.getOrder().getPrice().amount())
                .createdAt(orderCreatedEvent.getCreatedAt())
                .paymentOrderStatus(PaymentOrderStatus.PENDING.name())
                .build();
    }

    public OrderPaymentEventPayload orderCancelledEventToOrderPaymentEventPayload(OrderCancelledEvent
                                                                                          orderCancelledEvent) {
        return OrderPaymentEventPayload.builder()
                .customerId(orderCancelledEvent.getOrder().getCustomerId().id().toString())
                .orderId(orderCancelledEvent.getOrder().getId().id().toString())
                .price(orderCancelledEvent.getOrder().getPrice().amount())
                .createdAt(orderCancelledEvent.getCreatedAt())
                .paymentOrderStatus(PaymentOrderStatus.CANCELLED.name())
                .build();
    }

    public OrderApprovalEventPayload orderPaidEventToOrderApprovalEventPayload(OrderPaidEvent orderPaidEvent) {
        return OrderApprovalEventPayload.builder()
                .orderId(orderPaidEvent.getOrder().getId().id().toString())
                .restaurantId(orderPaidEvent.getOrder().getRestaurantId().id().toString())
                .restaurantOrderStatus(RestaurantOrderStatus.PAID.name())
                .products(orderPaidEvent.getOrder().getItems().stream().map(orderItem ->
                        OrderApprovalEventProduct.builder()
                                .id(orderItem.getProduct().getId().id().toString())
                                .quantity(orderItem.getQuantity())
                                .build()).collect(Collectors.toList()))
                .price(orderPaidEvent.getOrder().getPrice().amount())
                .createdAt(orderPaidEvent.getCreatedAt())
                .build();
    }
}
