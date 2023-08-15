package com.neonex.restaurant.service.domain.mapper;

import com.neonex.domain.valueobject.Money;
import com.neonex.domain.valueobject.OrderId;
import com.neonex.domain.valueobject.OrderStatus;
import com.neonex.domain.valueobject.RestaurantId;
import com.neonex.restaurant.service.domain.dto.RestaurantApprovalRequest;
import com.neonex.restaurant.service.domain.entity.OrderDetail;
import com.neonex.restaurant.service.domain.entity.Product;
import com.neonex.restaurant.service.domain.entity.Restaurant;
import com.neonex.restaurant.service.domain.event.OrderApprovalEvent;
import com.neonex.restaurant.service.domain.outbox.model.OrderEventPayload;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataMapper {
    public Restaurant restaurantApprovalRequestToRestaurant(RestaurantApprovalRequest
                                                                    restaurantApprovalRequest) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId(UUID.fromString(restaurantApprovalRequest.getRestaurantId())))
                .orderDetail(OrderDetail.builder()
                        .orderId(new OrderId(UUID.fromString(restaurantApprovalRequest.getOrderId())))
                        .products(restaurantApprovalRequest.getProducts().stream().map(
                                        product -> Product.builder()
                                                .productId(product.getId())
                                                .quantity(product.getQuantity())
                                                .build())
                                .collect(Collectors.toList()))
                        .totalAmount(new Money(restaurantApprovalRequest.getPrice()))
                        .orderStatus(OrderStatus.valueOf(restaurantApprovalRequest.getRestaurantOrderStatus().name()))
                        .build())
                .build();
    }

    public OrderEventPayload
    orderApprovalEventToOrderEventPayload(OrderApprovalEvent orderApprovalEvent) {
        return OrderEventPayload.builder()
                .orderId(orderApprovalEvent.getOrderApproval().getOrderId().id().toString())
                .restaurantId(orderApprovalEvent.getRestaurantId().id().toString())
                .orderApprovalStatus(orderApprovalEvent.getOrderApproval().getApprovalStatus().name())
                .createdAt(orderApprovalEvent.getCreatedAt())
                .failureMessages(orderApprovalEvent.getFailureMessages())
                .build();
    }
}
