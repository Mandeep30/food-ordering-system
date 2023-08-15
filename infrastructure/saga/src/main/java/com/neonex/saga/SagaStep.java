package com.neonex.saga;

import com.neonex.domain.event.DomainEvent;

public interface SagaStep<T> {
    void process(T data);

    void rollback(T data);
}
