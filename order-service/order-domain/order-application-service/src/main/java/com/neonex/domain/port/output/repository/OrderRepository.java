package com.neonex.domain.port.output.repository;

import com.neonex.domain.entity.Order;
import com.neonex.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findByTrackingId(TrackingId trackingId);
}
