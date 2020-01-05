package com.manish0890.skyline.service.service.impl;

import com.manish0890.skyline.service.document.BaseDocument;
import com.manish0890.skyline.service.exception.NotFoundException;
import com.manish0890.skyline.service.service.IBaseCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.util.StringUtils;

import java.util.Date;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

public class BaseCrudServiceImpl<T extends BaseDocument> implements IBaseCrudService<T> {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MongoRepository<T, String> repository;

    private final String documentName;

    public BaseCrudServiceImpl(MongoRepository<T, String> repository, Class<T> documentClass) {
        this.repository = repository;
        documentName = documentClass.getSimpleName();
    }

    /**
     * Saves document T to repository
     *
     * @param document {@link BaseDocument}
     * @return T {@link BaseDocument}
     */
    @Override
    public T create(T document) {
        notNull(document, documentName + " Cannot be empty");
        isTrue(StringUtils.isEmpty(document.getId()), "Cannot Create " + documentName + " with Ids already set");
        document.setCreatedDate(new Date());
        document.setUpdatedDate(null);
        logger.trace("Creating {} Id: {}", documentName, document.getId());
        return repository.save(document);
    }

    /**
     * Provides read functionality for a Document in an Index.
     *
     * @param id {@link BaseDocument#getId()}
     * @return T {@link BaseDocument}
     * @throws NotFoundException if Document not found.
     */
    @Override
    public T findById(String id) {
        isTrue(StringUtils.hasText(id), "Id cannot be blank");
        return repository.findById(id).orElseThrow(() -> new NotFoundException(documentName + " with id: " + id + " does not exist"));
    }

    /**
     * Returns true is document exists in repository
     * Returns false is document does not exist in repository
     *
     * @param id {@link BaseDocument#getId()}
     * @return boolean
     */
    @Override
    public boolean documentExists(String id) {
        return repository.existsById(id);
    }

    /**
     * Updates document T in repository
     *
     * @param document {@link BaseDocument}
     * @return T {@link BaseDocument}
     */
    @Override
    public T update(T document) {
        isTrue(StringUtils.hasText(document.getId()), "Cannot Update " + documentName + " with Blank Id");

        document.setCreatedDate(findById(document.getId()).getCreatedDate());
        document.setUpdatedDate(new Date());
        logger.trace("Updating {} Id: {}", documentName, document.getId());

        return repository.save(document);
    }

    /**
     * Deletes model T to ES repository
     *
     * @param id {@link String}
     */
    @Override
    public void delete(String id) {
        isTrue(StringUtils.hasText(id), "Cannot Delete " + documentName + " by Blank ID");
        if (!repository.existsById(id)) {
            throw new NotFoundException(documentName + " with id: " + id + " does not exist");
        } else {
            logger.trace("Deleting {} Id: {}", documentName, id);
            repository.deleteById(id);
        }
    }
}
