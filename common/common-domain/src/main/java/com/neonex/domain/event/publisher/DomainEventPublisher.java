package com.neonex.domain.event.publisher;

import com.neonex.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent<R>, R> {
    void publish(T domainEvent);
}
