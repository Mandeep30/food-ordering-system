package com.neonex.dataaccess.customer.adapter;

import com.neonex.dataaccess.customer.mapper.CustomerDataAccessMapper;
import com.neonex.dataaccess.customer.repository.CustomerEntityRepository;
import com.neonex.domain.entity.Customer;
import com.neonex.domain.port.output.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {
    private final CustomerEntityRepository customerEntityRepository;
    private final CustomerDataAccessMapper customerDataAccessMapper;

    public CustomerRepositoryImpl(CustomerEntityRepository customerEntityRepository,
                                  CustomerDataAccessMapper customerDataAccessMapper) {
        this.customerEntityRepository = customerEntityRepository;
        this.customerDataAccessMapper = customerDataAccessMapper;
    }

    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
        return customerEntityRepository.findCustomerById(customerId)
                .map(customerDataAccessMapper::customerEntityToCustomer);
    }
}
