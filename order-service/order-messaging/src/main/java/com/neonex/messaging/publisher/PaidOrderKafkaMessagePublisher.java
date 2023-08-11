package com.neonex.messaging.publisher;

import com.neonex.domain.config.OrderServiceConfigData;
import com.neonex.domain.event.OrderPaidEvent;
import com.neonex.domain.port.output.message.publisher.restaurant.OrderPaidRestaurantRequestMessagePublisher;
import com.neonex.kafka.order.avro.model.PaymentRequestAvroModel;
import com.neonex.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import com.neonex.kafka.producer.KafkaMessageHelper;
import com.neonex.kafka.producer.service.KafkaProducer;
import com.neonex.messaging.mapper.OrderMessagingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaidOrderKafkaMessagePublisher implements OrderPaidRestaurantRequestMessagePublisher {
    private final OrderMessagingMapper orderMessagingMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper orderMessageHelper;

    public PaidOrderKafkaMessagePublisher(OrderMessagingMapper orderMessagingMapper,
                                          OrderServiceConfigData orderServiceConfigData,
                                          KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer,
                                          KafkaMessageHelper orderMessageHelper) {
        this.orderMessagingMapper = orderMessagingMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderMessageHelper = orderMessageHelper;
    }

    @Override
    public void publish(OrderPaidEvent domainEvent) {
        var message = orderMessagingMapper.orderPaidEventToRestaurantApprovalRequestAvroModel(domainEvent);
        var orderId = domainEvent.getOrder().getId().toString();
        kafkaProducer.send(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                orderId,
                message,
                orderMessageHelper.getKafkaCallback(orderServiceConfigData.getRestaurantApprovalRequestTopicName(), message, orderId));
        log.info("Received order paid event and message sent to kafka for order id {}", domainEvent.getOrder().getId());
    }
}
