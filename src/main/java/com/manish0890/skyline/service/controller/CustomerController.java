package com.manish0890.skyline.service.controller;

import com.manish0890.skyline.service.document.Customer;
import com.manish0890.skyline.service.service.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private ICustomerService customerService;

    @PostMapping(value = "/customer")
    public ResponseEntity<Void> createCustomer(@Validated @RequestBody Customer customer){
        Customer createdCustomer = customerService.create(customer);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCustomer.getId())
                .toUri();

        LOGGER.info("Returned Customer with Id: {}", customer.getId());
        return ResponseEntity
                .created(uri)
                .build();
    }
}
