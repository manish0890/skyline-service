package com.manish0890.skyline.service.service;

import com.manish0890.skyline.service.repository.ICustomerRepository;
import com.manish0890.skyline.service.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.manish0890.skyline.service.utility.TestUtility.randomId;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplUnitTest {
    @Mock
    private ICustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void verifyCustomerInheritsBaseDocument() {
        customerService.documentExists(randomId());
        verify(customerRepository, times(1)).existsById(anyString());

    }
}
