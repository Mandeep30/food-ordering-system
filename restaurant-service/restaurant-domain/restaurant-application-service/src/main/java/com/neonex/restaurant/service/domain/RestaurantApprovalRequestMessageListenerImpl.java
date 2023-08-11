package com.neonex.restaurant.service.domain;

import com.neonex.restaurant.service.domain.dto.RestaurantApprovalRequest;
import com.neonex.restaurant.service.domain.event.OrderApprovalEvent;
import com.neonex.restaurant.service.domain.event.OrderApprovedEvent;
import com.neonex.restaurant.service.domain.event.OrderRejectedEvent;
import com.neonex.restaurant.service.domain.ports.input.message.listener.RestaurantApprovalRequestMessageListener;
import com.neonex.restaurant.service.domain.ports.output.message.publisher.OrderApprovedMessagePublisher;
import com.neonex.restaurant.service.domain.ports.output.message.publisher.OrderRejectedMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RestaurantApprovalRequestMessageListenerImpl implements RestaurantApprovalRequestMessageListener {

    private final RestaurantApprovalRequestHelper restaurantApprovalRequestHelper;

    private final OrderApprovedMessagePublisher orderApprovedMessagePublisher;
    private final OrderRejectedMessagePublisher orderRejectedMessagePublisher;

    public RestaurantApprovalRequestMessageListenerImpl(RestaurantApprovalRequestHelper
                                                                restaurantApprovalRequestHelper,
                                                        OrderApprovedMessagePublisher orderApprovedMessagePublisher,
                                                        OrderRejectedMessagePublisher orderRejectedMessagePublisher) {
        this.restaurantApprovalRequestHelper = restaurantApprovalRequestHelper;
        this.orderApprovedMessagePublisher = orderApprovedMessagePublisher;
        this.orderRejectedMessagePublisher = orderRejectedMessagePublisher;
    }

    @Override
    public void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest) {
        OrderApprovalEvent orderApprovalEvent =
                restaurantApprovalRequestHelper.persistOrderApproval(restaurantApprovalRequest);
        fireEvent(orderApprovalEvent);
    }

    private void fireEvent(OrderApprovalEvent orderApprovalEvent) {
        if (orderApprovalEvent instanceof OrderApprovedEvent event) {
            orderApprovedMessagePublisher.publish(event);
        } else if (orderApprovalEvent instanceof OrderRejectedEvent event) {
            orderRejectedMessagePublisher.publish(event);
        }
    }
}
