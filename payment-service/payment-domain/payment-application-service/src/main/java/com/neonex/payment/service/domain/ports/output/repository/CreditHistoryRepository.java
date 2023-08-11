package com.neonex.payment.service.domain.ports.output.repository;

import com.neonex.domain.valueobject.CustomerId;
import com.neonex.payment.service.domain.entity.CreditHistory;

import java.util.List;
import java.util.Optional;

public interface CreditHistoryRepository {

    void save(CreditHistory creditHistory);

    Optional<List<CreditHistory>> findByCustomerId(CustomerId customerId);
}
