package com.neonex.domain;

import com.neonex.domain.dto.track.TrackOrderQuery;
import com.neonex.domain.dto.track.TrackOrderResponse;
import com.neonex.domain.exception.OrderNotFoundException;
import com.neonex.domain.mapper.OrderDataMapper;
import com.neonex.domain.port.output.repository.OrderRepository;
import com.neonex.domain.valueobject.TrackingId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
class TrackOrderQueryHandler {
    private final OrderRepository orderRepository;
    private final OrderDataMapper orderDataMapper;

    TrackOrderQueryHandler(OrderRepository orderRepository, OrderDataMapper orderDataMapper) {
        this.orderRepository = orderRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        var optionalOrder = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));
        if (optionalOrder.isEmpty()) {
            throw new OrderNotFoundException("Could not find order with tracking id " + trackOrderQuery.getOrderTrackingId());
        }
        return orderDataMapper.orderToTrackOrderResponse(optionalOrder.get());
    }
}
