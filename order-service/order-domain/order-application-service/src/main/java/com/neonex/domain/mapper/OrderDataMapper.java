package com.neonex.domain.mapper;

import com.neonex.domain.dto.create.CreateOrderCommand;
import com.neonex.domain.dto.create.CreateOrderResponse;
import com.neonex.domain.dto.track.TrackOrderResponse;
import com.neonex.domain.entity.Order;
import com.neonex.domain.entity.OrderItem;
import com.neonex.domain.entity.Product;
import com.neonex.domain.entity.Restaurant;
import com.neonex.domain.valueobject.CustomerId;
import com.neonex.domain.valueobject.Money;
import com.neonex.domain.valueobject.ProductId;
import com.neonex.domain.valueobject.RestaurantId;
import com.neonex.domain.valueobject.StreetAddress;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
}
