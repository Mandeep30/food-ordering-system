package com.neonex.domain.port.input;

import com.neonex.domain.dto.create.CreateOrderCommand;
import com.neonex.domain.dto.create.CreateOrderResponse;
import com.neonex.domain.dto.track.TrackOrderQuery;
import com.neonex.domain.dto.track.TrackOrderResponse;

import javax.validation.Valid;

/**
 * Input port of the hexagonal architecture,
 * implemented by domain layer and called by the clients(like REST call) of the domain layer
 */
public interface OrderApplicationService {
    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
