package com.manish0890.skyline.service.service;

import com.manish0890.skyline.service.document.BaseDocument;

public interface IBaseCrudService<T extends BaseDocument> {
    T findById(String id);

    boolean documentExists(String id);

    T create(T modelToSave);

    T update(T modelToUpdate);

    void delete(String id);
}
