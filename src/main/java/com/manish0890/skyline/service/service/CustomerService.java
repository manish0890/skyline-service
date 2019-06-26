package com.manish0890.skyline.service.service;

import com.manish0890.skyline.service.document.Customer;
import com.manish0890.skyline.service.exception.NotFoundException;
import com.manish0890.skyline.service.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(Customer customer){
        Assert.notNull(customer, "While creation customer cannot be null");
        Assert.isTrue(StringUtils.isEmpty(customer.getId()), "While creating Customer, id should be empty but received " + customer.getId());

        return customerRepository.save(customer);
    }

    public Customer find(String id){
        Assert.isTrue(StringUtils.hasText(id), "id cannot be null");
        Optional<Customer> customer = customerRepository.findById(id);

        if (!customer.isPresent()){
            LOGGER.warn("Could not find Customer with Id {}", id);
            throw new NotFoundException("Could not find Customer with Id "+ id);
        }

        return customer.get();
    }
}
