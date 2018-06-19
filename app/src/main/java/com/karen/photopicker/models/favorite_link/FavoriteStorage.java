package com.karen.photopicker.models.favorite_link;

import com.karen.photopicker.models.link.Link;
import com.karen.photopicker.object_storage.ObjectStorage;

import java.io.Serializable;
import java.util.List;

public class FavoriteStorage implements Serializable {
    private static final String KEY_FAVORITE_LINK_STORAGE = "FAVORITE_LINK_STORAGE";
    private List<Favorite> favorites;

    public FavoriteStorage(List<Favorite> links) {
        this.favorites = links;
        save(this);
    }

    private void save(FavoriteStorage storage) {
        ObjectStorage.getInstance().put(ObjectStorage.key(KEY_FAVORITE_LINK_STORAGE, FavoriteStorage.class), storage);
    }

    public static FavoriteStorage restore() {
        return ObjectStorage.getInstance().get(ObjectStorage.key(KEY_FAVORITE_LINK_STORAGE, FavoriteStorage.class));
    }

    public List<Favorite> getLinks() {
        return favorites;
    }
}
