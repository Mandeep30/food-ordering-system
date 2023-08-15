package com.neonex.outbox;

public interface OutboxScheduler {
    void processOutboxMessage();
}
