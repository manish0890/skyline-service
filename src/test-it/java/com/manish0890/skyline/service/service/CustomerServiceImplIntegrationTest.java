package com.manish0890.skyline.service.service;

import com.manish0890.skyline.service.TestConfig;
import com.manish0890.skyline.service.document.Customer;
import com.manish0890.skyline.service.repository.ICustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.manish0890.skyline.service.utility.TestUtility.customerWithTestValues;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class CustomerServiceImplIntegrationTest {
    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private ICustomerService customerService;

    @Test
    void testCreate() {
        // Create Expectation
        Customer testCustomer = customerWithTestValues();
        customerService.create(testCustomer);
        assertNotNull(testCustomer.getId());
        assertNotNull(testCustomer.getCreatedDate());
        assertNull(testCustomer.getUpdatedDate());

        // Verify created Expectation
        Customer readCustomer = customerRepository.findById(testCustomer.getId()).orElse(null);
        assertTrue(testCustomer.equals(readCustomer));
    }
}
