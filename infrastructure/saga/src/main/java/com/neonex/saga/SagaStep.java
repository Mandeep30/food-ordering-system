package com.neonex.saga;

import com.neonex.domain.event.DomainEvent;

public interface SagaStep<T, S extends DomainEvent<P>, R extends DomainEvent<Q>, P, Q> {
    S process(T data);

    R rollback(T data);
}
