package com.manish0890.skyline.service.repository;

import com.manish0890.skyline.service.document.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}
