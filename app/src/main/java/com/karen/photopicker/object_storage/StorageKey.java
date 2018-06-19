package com.karen.photopicker.object_storage;

import java.io.Serializable;

public class StorageKey<T extends Serializable> {

    final String identifier;

    final Class<T> type;

    public StorageKey(String identifier, Class<T> type) {
        this.identifier = identifier;
        this.type = type;
    }
}
