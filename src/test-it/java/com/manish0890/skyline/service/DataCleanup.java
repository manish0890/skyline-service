package com.manish0890.skyline.service;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This class cleans the test data after each test
public abstract class DataCleanup {
    @Autowired
    List<MongoRepository> repositories;

    @AfterEach
    public void cleanUpRepositories() {
        repositories.forEach(repository -> {
            repository.deleteAll();
            assertEquals(0, repository.findAll().size(),
                    "Some documents were not wiped from db after the test");
        });
    }
}