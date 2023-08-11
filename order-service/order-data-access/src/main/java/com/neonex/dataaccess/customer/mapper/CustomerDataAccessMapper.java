package com.neonex.dataaccess.customer.mapper;

import com.neonex.dataaccess.customer.entity.CustomerEntity;
import com.neonex.domain.entity.Customer;
import com.neonex.domain.valueobject.CustomerId;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataAccessMapper {
    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return new Customer(new CustomerId(customerEntity.getId()));
    }
}
