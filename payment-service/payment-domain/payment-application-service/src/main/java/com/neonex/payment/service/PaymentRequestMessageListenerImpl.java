package com.neonex.payment.service;

import com.neonex.payment.service.domain.dto.PaymentRequest;
import com.neonex.payment.service.domain.event.PaymentCancelledEvent;
import com.neonex.payment.service.domain.event.PaymentCompletedEvent;
import com.neonex.payment.service.domain.event.PaymentEvent;
import com.neonex.payment.service.domain.event.PaymentFailedEvent;
import com.neonex.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener;
import com.neonex.payment.service.domain.ports.output.message.publisher.PaymentCancelledMessagePublisher;
import com.neonex.payment.service.domain.ports.output.message.publisher.PaymentCompletedMessagePublisher;
import com.neonex.payment.service.domain.ports.output.message.publisher.PaymentFailedMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener {

    private final PaymentRequestHelper paymentRequestHelper;
    private final PaymentFailedMessagePublisher paymentFailedMessagePublisher;
    private final PaymentCompletedMessagePublisher paymentCompletedMessagePublisher;
    private final PaymentCancelledMessagePublisher paymentCancelledMessagePublisher;


    public PaymentRequestMessageListenerImpl(PaymentRequestHelper paymentRequestHelper,
                                             PaymentFailedMessagePublisher paymentFailedMessagePublisher,
                                             PaymentCompletedMessagePublisher paymentCompletedMessagePublisher, PaymentCancelledMessagePublisher paymentCancelledMessagePublisher) {
        this.paymentRequestHelper = paymentRequestHelper;
        this.paymentFailedMessagePublisher = paymentFailedMessagePublisher;
        this.paymentCompletedMessagePublisher = paymentCompletedMessagePublisher;
        this.paymentCancelledMessagePublisher = paymentCancelledMessagePublisher;
    }

    @Override
    public void completePayment(PaymentRequest paymentRequest) {
        PaymentEvent paymentEvent = paymentRequestHelper.persistPayment(paymentRequest);
        fireEvent(paymentEvent);
    }

    @Override
    public void cancelPayment(PaymentRequest paymentRequest) {
        PaymentEvent paymentEvent = paymentRequestHelper.persistCancelPayment(paymentRequest);
        fireEvent(paymentEvent);
    }

    private void fireEvent(PaymentEvent paymentEvent) {
        log.info("Publishing payment event with payment id: {} and order id: {}",
                paymentEvent.getPayment().getId().id(),
                paymentEvent.getPayment().getOrderId().id());
        if (paymentEvent instanceof PaymentCompletedEvent event) {
            paymentCompletedMessagePublisher.publish(event);
        } else if (paymentEvent instanceof PaymentCancelledEvent event) {
            paymentCancelledMessagePublisher.publish(event);
        } else if (paymentEvent instanceof PaymentFailedEvent event) {
            paymentFailedMessagePublisher.publish(event);
        }
    }
}
