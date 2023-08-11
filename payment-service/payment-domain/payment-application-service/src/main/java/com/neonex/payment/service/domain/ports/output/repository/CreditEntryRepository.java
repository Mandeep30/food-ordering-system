package com.neonex.payment.service.domain.ports.output.repository;

import com.neonex.domain.valueobject.CustomerId;
import com.neonex.payment.service.domain.entity.CreditEntry;

import java.util.Optional;

public interface CreditEntryRepository {

    CreditEntry save(CreditEntry creditEntry);

    Optional<CreditEntry> findByCustomerId(CustomerId customerId);
}
