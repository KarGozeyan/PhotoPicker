package com.karen.photopicker.models.link;

import com.karen.photopicker.object_storage.ObjectStorage;

import java.io.Serializable;
import java.util.List;

public class FavoriteStorage implements Serializable{
    private static final String KEY_FAV_STORAGE = "FAV_STORAGE";
    private List<String> links;

    public FavoriteStorage(List<String> links) {
        this.links = links;
        save(this);
    }

    private void save(FavoriteStorage storage) {
        ObjectStorage.getInstance().put(ObjectStorage.key(KEY_FAV_STORAGE, FavoriteStorage.class), storage);
    }

    public static FavoriteStorage restore() {
        return ObjectStorage.getInstance().get(ObjectStorage.key(KEY_FAV_STORAGE, FavoriteStorage.class));
    }

    public List<String> getLinks() {
        return links;
    }
}
