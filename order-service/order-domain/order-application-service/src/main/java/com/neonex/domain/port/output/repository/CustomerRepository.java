package com.neonex.domain.port.output.repository;

import com.neonex.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findCustomer(UUID customerId);
}
