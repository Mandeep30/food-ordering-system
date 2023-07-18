package com.neonex.domain;

import com.neonex.domain.dto.create.CreateOrderCommand;
import com.neonex.domain.dto.create.CreateOrderResponse;
import com.neonex.domain.exception.OrderDomainException;
import com.neonex.domain.mapper.OrderDataMapper;
import com.neonex.domain.port.output.repository.CustomerRepository;
import com.neonex.domain.port.output.repository.OrderRepository;
import com.neonex.domain.port.output.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
class OrderCreateCommandHandler {
    private final OrderDomainService orderDomainService;
    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final OrderDataMapper orderDataMapper;

    public OrderCreateCommandHandler(OrderDomainService orderDomainService,
                                     RestaurantRepository restaurantRepository,
                                     CustomerRepository customerRepository,
                                     OrderRepository orderRepository,
                                     OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.restaurantRepository = restaurantRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        var customer = customerRepository.findCustomer(createOrderCommand.getCustomerId());
        if (customer.isEmpty()) {
            throw new OrderDomainException("Customer doesn't exist with id " + createOrderCommand.getCustomerId());
        }
        var restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        var optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
        if (optionalRestaurant.isEmpty()) {
            throw new OrderDomainException("Restaurant doesn't exist with id " + createOrderCommand.getRestaurantId());
        }
        var restaurantDomainEntity = optionalRestaurant.get();
        var orderDomainEntity = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        var orderCreatedEvent = orderDomainService.validateAndInitiateOrder(orderDomainEntity, restaurantDomainEntity);
        var order = orderRepository.save(orderDomainEntity);
        if (order == null) {
            throw new OrderDomainException("Could not save order");
        }
        log.info("Order is saved with id " + order.getId());
        return orderDataMapper.orderToCreateOrderResponse(order);
    }
}
