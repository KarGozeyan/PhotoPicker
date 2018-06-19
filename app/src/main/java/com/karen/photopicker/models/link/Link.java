package com.karen.photopicker.models.link;

import java.io.Serializable;

public class Link implements Serializable {
    private String url, id;
    private boolean isFavorite;

    public Link(String url) {
        this.url = url;
        isFavorite = false;
    }

    public String getUrl() {
        return url;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}
