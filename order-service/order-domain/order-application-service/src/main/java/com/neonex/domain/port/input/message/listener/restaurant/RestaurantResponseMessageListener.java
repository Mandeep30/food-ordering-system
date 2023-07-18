package com.neonex.domain.port.input.message.listener.restaurant;

import com.neonex.domain.dto.message.RestaurantApprovalResponse;

public interface RestaurantResponseMessageListener {
    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);

    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
