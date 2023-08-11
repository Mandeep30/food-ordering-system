package com.neonex.messaging.publisher;

import com.neonex.domain.config.OrderServiceConfigData;
import com.neonex.domain.event.OrderCancelledEvent;
import com.neonex.domain.port.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.neonex.kafka.order.avro.model.PaymentRequestAvroModel;
import com.neonex.kafka.producer.KafkaMessageHelper;
import com.neonex.kafka.producer.service.KafkaProducer;
import com.neonex.messaging.mapper.OrderMessagingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CancelOrderKafkaMessagePublisher implements OrderCancelledPaymentRequestMessagePublisher {
    private final OrderMessagingMapper orderMessagingMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper orderMessageHelper;

    public CancelOrderKafkaMessagePublisher(OrderMessagingMapper orderMessagingMapper,
                                            OrderServiceConfigData orderServiceConfigData,
                                            KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer,
                                            KafkaMessageHelper orderMessageHelper) {
        this.orderMessagingMapper = orderMessagingMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderMessageHelper = orderMessageHelper;
    }

    @Override
    public void publish(OrderCancelledEvent domainEvent) {
        var message = orderMessagingMapper.orderCancelledEventToPaymentRequestAvroModel(domainEvent);
        var orderId = domainEvent.getOrder().getId().toString();
        kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(), orderId,
                message, orderMessageHelper.getKafkaCallback(orderServiceConfigData.getPaymentRequestTopicName(), message, orderId));
        log.info("Received order cancelled event and message sent to kafka for order id {}", orderId);
    }
}
