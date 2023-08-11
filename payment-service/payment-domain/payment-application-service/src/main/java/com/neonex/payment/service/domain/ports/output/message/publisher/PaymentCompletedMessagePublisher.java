package com.neonex.payment.service.domain.ports.output.message.publisher;

import com.neonex.domain.event.publisher.DomainEventPublisher;
import com.neonex.payment.service.domain.entity.Payment;
import com.neonex.payment.service.domain.event.PaymentCompletedEvent;

public interface PaymentCompletedMessagePublisher extends DomainEventPublisher<PaymentCompletedEvent, Payment> {
}
