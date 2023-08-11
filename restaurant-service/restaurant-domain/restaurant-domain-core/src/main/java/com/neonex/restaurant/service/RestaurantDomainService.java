package com.neonex.restaurant.service;

import com.neonex.restaurant.service.domain.entity.Restaurant;
import com.neonex.restaurant.service.domain.event.OrderApprovalEvent;

import java.util.List;

public interface RestaurantDomainService {

    OrderApprovalEvent validateOrder(Restaurant restaurant, List<String> failureMessages);
}
