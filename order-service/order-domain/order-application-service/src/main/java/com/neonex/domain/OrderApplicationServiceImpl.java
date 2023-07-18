package com.neonex.domain;

import com.neonex.domain.dto.create.CreateOrderCommand;
import com.neonex.domain.dto.create.CreateOrderResponse;
import com.neonex.domain.dto.track.TrackOrderQuery;
import com.neonex.domain.dto.track.TrackOrderResponse;
import com.neonex.domain.port.input.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
class OrderApplicationServiceImpl implements OrderApplicationService {
    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final TrackOrderQueryHandler trackOrderQueryHandler;

    OrderApplicationServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler, TrackOrderQueryHandler trackOrderQueryHandler) {
        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.trackOrderQueryHandler = trackOrderQueryHandler;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return trackOrderQueryHandler.trackOrder(trackOrderQuery);
    }
}
