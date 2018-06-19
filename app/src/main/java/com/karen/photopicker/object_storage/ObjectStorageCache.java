
package com.karen.photopicker.object_storage;

import android.util.LruCache;


public class ObjectStorageCache extends LruCache<StorageKey<?>, Object> {

    private static final int maxSize = 4 * 1024 * 1024; // 4MiB

    public ObjectStorageCache() {
        super(maxSize);
    }
}
