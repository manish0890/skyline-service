package com.manish0890.skyline.service.service;

import com.manish0890.skyline.service.document.BaseDocument;
import com.manish0890.skyline.service.exception.NotFoundException;
import com.manish0890.skyline.service.service.impl.BaseCrudServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.Optional;

import static com.manish0890.skyline.service.utility.TestUtility.randomDate;
import static com.manish0890.skyline.service.utility.TestUtility.randomId;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseCrudServiceImplUnitTest {
    // Create a Test Document class
    private class TestDocument extends BaseDocument {
    }

    // Create a Test Document Repository
    private interface ITestDocumentRepository extends MongoRepository<TestDocument, String> {
    }

    @Mock
    private ITestDocumentRepository testDocumentRepository;

    @InjectMocks
    private BaseCrudServiceImpl<TestDocument> baseCrudServiceImpl = new BaseCrudServiceImpl<>(testDocumentRepository, TestDocument.class);

    @Test
    void create() {
        TestDocument testDocument = new TestDocument();
        testDocument.setUpdatedDate(new Date());

        // Stub the response coming from ES Repository
        when(testDocumentRepository.save(any())).thenAnswer((Answer) invocationOnMock -> {
            TestDocument object = invocationOnMock.getArgument(0);
            object.setId(randomId());
            return object;
        });

        // Exercise the method
        TestDocument createdObject = baseCrudServiceImpl.create(testDocument);
        assertNotNull(createdObject);
        assertNull(createdObject.getUpdatedDate());
        assertNotNull(createdObject.getCreatedDate());
    }

    @Test
    void createWithExistingId() {
        TestDocument TestDocument = new TestDocument();
        TestDocument.setId(randomId());

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> baseCrudServiceImpl.create(TestDocument));
        assertEquals("Cannot Create TestDocument with Ids already set", e.getMessage());
    }

    @Test
    void findById() {
        when(testDocumentRepository.findById(any())).thenAnswer((Answer) invocationOnMock -> Optional.of(new TestDocument()));
        assertNotNull(baseCrudServiceImpl.findById(randomId()));
    }

    @Test
    void findByNullId() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> baseCrudServiceImpl.findById(null));
        assertEquals("Id cannot be blank", e.getMessage());

        e = assertThrows(IllegalArgumentException.class, () -> baseCrudServiceImpl.findById(""));
        assertEquals("Id cannot be blank", e.getMessage());
    }

    @Test
    void findByNonExistentId() {
        String invalidId = randomId();
        NotFoundException e = assertThrows(NotFoundException.class, () -> baseCrudServiceImpl.findById(invalidId));
        assertEquals(e.getMessage(), "TestDocument with id: " + invalidId + " does not exist");
    }

    @Test
    void documentExists() {
        baseCrudServiceImpl.documentExists(randomId());
        verify(testDocumentRepository, times(1)).existsById(anyString());
    }

    @Test
    void update() {
        TestDocument TestDocument = new TestDocument();
        TestDocument.setId(randomId());
        TestDocument.setCreatedDate(randomDate());

        // Stub the response coming from ES Repository
        when(testDocumentRepository.findById(eq(TestDocument.getId()))).thenReturn(Optional.of(TestDocument));
        when(testDocumentRepository.save(any())).thenAnswer((Answer) invocationOnMock -> invocationOnMock.getArgument(0));

        // Exercise the method
        TestDocument updatedObject = baseCrudServiceImpl.update(TestDocument);
        assertNotNull(updatedObject.getUpdatedDate());
        assertEquals(TestDocument.getCreatedDate(), updatedObject.getCreatedDate());
    }

    @Test
    void updateUsingNullId() {
        String expectedErrorMessage = "Cannot Update TestDocument with Blank Id";

        TestDocument TestDocument = new TestDocument();
        TestDocument.setId(null);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> baseCrudServiceImpl.update(TestDocument));
        assertEquals(expectedErrorMessage, e.getMessage());

        TestDocument.setId("");
        e = assertThrows(IllegalArgumentException.class, () -> baseCrudServiceImpl.update(TestDocument));
        assertEquals(expectedErrorMessage, e.getMessage());
    }

    @Test
    void updateNonExistent() {
        TestDocument TestDocument = new TestDocument();
        TestDocument.setId(randomId());

        when(testDocumentRepository.findById(eq(TestDocument.getId()))).thenReturn(Optional.empty());

        NotFoundException e = assertThrows(NotFoundException.class, () -> baseCrudServiceImpl.update(TestDocument));
        assertEquals("TestDocument with id: " + TestDocument.getId() + " does not exist", e.getMessage());
    }

    @Test
    void delete() {
        when(testDocumentRepository.existsById(any())).thenReturn(true);

        baseCrudServiceImpl.delete(randomId());
        verify(testDocumentRepository, times(1)).deleteById(anyString());
    }

    @Test
    void deleteNonExistent() {
        when(testDocumentRepository.existsById(any())).thenReturn(false);

        String invalidId = randomId();
        NotFoundException e = assertThrows(NotFoundException.class, () -> baseCrudServiceImpl.delete(invalidId));
        assertEquals(e.getMessage(), "TestDocument with id: " + invalidId + " does not exist");
        verify(testDocumentRepository, times(0)).deleteById(anyString());
    }
}
