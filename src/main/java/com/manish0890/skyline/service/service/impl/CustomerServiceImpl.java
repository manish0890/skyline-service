package com.manish0890.skyline.service.service.impl;

import com.manish0890.skyline.service.document.Customer;
import com.manish0890.skyline.service.repository.ICustomerRepository;
import com.manish0890.skyline.service.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends BaseCrudServiceImpl<Customer> implements ICustomerService {

    @Autowired
    public CustomerServiceImpl(ICustomerRepository customerRepository) {
        super(customerRepository, Customer.class);
    }
}
