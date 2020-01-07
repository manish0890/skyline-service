package com.manish0890.skyline.service.service;

import com.manish0890.skyline.service.DataCleanup;
import com.manish0890.skyline.service.TestConfig;
import com.manish0890.skyline.service.document.Customer;
import com.manish0890.skyline.service.exception.NotFoundException;
import com.manish0890.skyline.service.repository.ICustomerRepository;
import com.manish0890.skyline.service.service.impl.BaseCrudServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;

import java.util.Date;

import static com.manish0890.skyline.service.utility.TestUtility.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class BaseCrudServiceImplIntegrationTest extends DataCleanup {

    // Just autowiring CustomerRepository so that we can get repository without special test bean creation
    @Autowired
    private ICustomerRepository customerRepository;

    // Using Customer as a document for Crud operation since it inherits from BaseDocument and also corresponds to CustomerRepository
    private Customer testDocument;
    private BaseCrudServiceImpl<Customer> baseCrudServiceImpl;

    @PostConstruct
    void init() {
        baseCrudServiceImpl = new BaseCrudServiceImpl<>(customerRepository, Customer.class);
    }

    @BeforeEach
    void beforeEach() {
        testDocument = customerWithTestValues();
    }

    @Test
    void testCreateAndFindById() {
        // Create Document
        baseCrudServiceImpl.create(testDocument);

        // Verify properties that are set in BaseCrudServiceImpl::create
        assertNotNull(testDocument.getId());
        assertNotNull(testDocument.getCreatedDate());
        assertNull(testDocument.getUpdatedDate());

        // Verify Document was in fact created in DB
        assertNotNull(baseCrudServiceImpl.findById(testDocument.getId()));
    }

    @Test
    void testUpdate() {
        // Create Document
        baseCrudServiceImpl.create(testDocument);
        assertNotNull(testDocument.getId());

        Date createdDate = testDocument.getCreatedDate();
        String updatedName = randomAlphabetic();
        testDocument.setName(updatedName);

        // Update Document
        assertNotNull(baseCrudServiceImpl.update(testDocument));

        // Read updated Document
        Customer updatedDocument = baseCrudServiceImpl.findById(testDocument.getId());
        assertNotNull(updatedDocument);

        // Verify updated Date is not null
        assertNotNull(updatedDocument.getUpdatedDate());

        // Verify that created date is not changed
        assertEquals(createdDate.getTime(), updatedDocument.getCreatedDate().getTime());

        // Verify that name was updated
        assertEquals(updatedName, updatedDocument.getName());
    }

    @Test
    void testDelete() {
        // Create Document in DB
        baseCrudServiceImpl.create(testDocument);
        assertNotNull(testDocument.getId());

        // Get Document by Id
        baseCrudServiceImpl.delete(testDocument.getId());

        // Verify that document was in fact deleted from DB
        assertThrows(NotFoundException.class, () -> baseCrudServiceImpl.findById(testDocument.getId()));
    }
}
