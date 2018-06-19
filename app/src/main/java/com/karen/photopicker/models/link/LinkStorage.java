package com.karen.photopicker.models.link;

import com.karen.photopicker.object_storage.ObjectStorage;

import java.io.Serializable;
import java.util.List;

public class LinkStorage implements Serializable {
    private static final String KEY_LINK_STORAGE = "LINK_STORAGE";
    private List<Link> links;

    public LinkStorage(List<Link> links) {
        this.links = links;
        save(this);
    }

    private void save(LinkStorage storage) {
        ObjectStorage.getInstance().put(ObjectStorage.key(KEY_LINK_STORAGE, LinkStorage.class), storage);
    }

    public static LinkStorage restore() {
        return ObjectStorage.getInstance().get(ObjectStorage.key(KEY_LINK_STORAGE, LinkStorage.class));
    }

    public List<Link> getLinks() {
        return links;
    }
}
