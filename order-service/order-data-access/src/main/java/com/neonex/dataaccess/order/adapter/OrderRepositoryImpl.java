package com.neonex.dataaccess.order.adapter;

import com.neonex.dataaccess.order.mapper.OrderDataAccessMapper;
import com.neonex.dataaccess.order.repository.OrderEntityRepository;
import com.neonex.domain.entity.Order;
import com.neonex.domain.port.output.repository.OrderRepository;
import com.neonex.domain.valueobject.OrderId;
import com.neonex.domain.valueobject.TrackingId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderEntityRepository orderEntityRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;

    public OrderRepositoryImpl(OrderEntityRepository orderEntityRepository, OrderDataAccessMapper orderDataAccessMapper) {
        this.orderEntityRepository = orderEntityRepository;
        this.orderDataAccessMapper = orderDataAccessMapper;
    }

    @Override
    public Order save(Order order) {
        return orderDataAccessMapper.orderEntityToOrder(
                orderEntityRepository.save(orderDataAccessMapper.orderToOrderEntity(order)));
    }

    @Override
    public Optional<Order> findByTrackingId(TrackingId trackingId) {
        return orderEntityRepository.findByTrackingId(trackingId.id()).map(orderDataAccessMapper::orderEntityToOrder);
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return orderEntityRepository.findById(orderId.id()).map(orderDataAccessMapper::orderEntityToOrder);
    }
}
