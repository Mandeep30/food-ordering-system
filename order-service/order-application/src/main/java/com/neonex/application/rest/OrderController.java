package com.neonex.application.rest;

import com.neonex.domain.dto.create.CreateOrderCommand;
import com.neonex.domain.dto.create.CreateOrderResponse;
import com.neonex.domain.dto.track.TrackOrderQuery;
import com.neonex.domain.dto.track.TrackOrderResponse;
import com.neonex.domain.port.input.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/orders", produces = "application/vnd.api.v1+json")
public class OrderController {
    private final OrderApplicationService orderApplicationService;

    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }

    @PostMapping
    public CreateOrderResponse createOrder(@RequestBody CreateOrderCommand createOrderCommand) {
        log.info("Creating order with customer id {} and restaurant id {} ", createOrderCommand.getCustomerId(), createOrderCommand.getRestaurantId());
        return orderApplicationService.createOrder(createOrderCommand);
    }

    @GetMapping("/{trackingId}")
    public TrackOrderResponse trackOrder(@PathVariable UUID trackingId) {
        log.info("Returning order status with tracking id {} ", trackingId);
        return orderApplicationService.trackOrder(TrackOrderQuery.builder().orderTrackingId(trackingId).build());
    }
}

