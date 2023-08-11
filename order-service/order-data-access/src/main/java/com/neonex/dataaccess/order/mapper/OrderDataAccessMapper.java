package com.neonex.dataaccess.order.mapper;

import com.neonex.dataaccess.order.entity.OrderAddressEntity;
import com.neonex.dataaccess.order.entity.OrderEntity;
import com.neonex.dataaccess.order.entity.OrderItemEntity;
import com.neonex.domain.entity.Order;
import com.neonex.domain.entity.OrderItem;
import com.neonex.domain.entity.Product;
import com.neonex.domain.valueobject.CustomerId;
import com.neonex.domain.valueobject.Money;
import com.neonex.domain.valueobject.OrderId;
import com.neonex.domain.valueobject.OrderItemId;
import com.neonex.domain.valueobject.ProductId;
import com.neonex.domain.valueobject.RestaurantId;
import com.neonex.domain.valueobject.StreetAddress;
import com.neonex.domain.valueobject.TrackingId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class OrderDataAccessMapper {
    public OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity
                .builder()
                .id(order.getId().id())
                .customerId(order.getCustomerId().id())
                .restaurantId(order.getRestaurantId().id())
                .trackingId(order.getTrackingId().id())
                .address(OrderAddressEntity.builder()
                        .city(order.getStreetAddress().city())
                        .postalCode(order.getStreetAddress().postalCode())
                        .street(order.getStreetAddress().street())
                        .id(order.getStreetAddress().id())
                        .build())
                .price(order.getPrice().amount())
                .items(order.getItems().stream()
                        .map(item -> OrderItemEntity
                                .builder()
                                .id(item.getId().id())
                                .productId(item.getProduct().getId().id())
                                .quantity(item.getQuantity())
                                .subTotal(item.getSubTotal().amount())
                                .price(item.getPrice().amount())
                                .build())
                        .toList())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages() != null ?
                        String.join(",", order.getFailureMessages()) : "")
                .build();
        orderEntity.getAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(item -> item.setOrder(orderEntity));
        return orderEntity;
    }

    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.Builder
                .builder()
                .orderId(new OrderId(orderEntity.getId()))
                .restaurantId(new RestaurantId(orderEntity.getRestaurantId()))
                .customerId(new CustomerId(orderEntity.getCustomerId()))
                .price(new Money(orderEntity.getPrice()))
                .trackingId(new TrackingId(orderEntity.getTrackingId()))
                .orderStatus(orderEntity.getOrderStatus())
                .failureMessages(!orderEntity.getFailureMessages().isEmpty() ?
                        new ArrayList<>(Arrays.asList(orderEntity.getFailureMessages().split(","))) : new ArrayList<>())
                .streetAddress(new StreetAddress(orderEntity.getAddress().getId(),
                        orderEntity.getAddress().getStreet(),
                        orderEntity.getAddress().getPostalCode(),
                        orderEntity.getAddress().getCity()))
                .items(orderEntity.getItems().stream()
                        .map(orderItemEntity -> OrderItem.Builder
                                .builder()
                                .orderItemId(new OrderItemId(orderItemEntity.getId()))
                                .productId(new Product(new ProductId(orderItemEntity.getProductId())))
                                .price(new Money(orderItemEntity.getPrice()))
                                .quantity(orderItemEntity.getQuantity())
                                .subTotal(new Money(orderItemEntity.getSubTotal()))
                                .build())
                        .toList())
                .build();
    }
}
