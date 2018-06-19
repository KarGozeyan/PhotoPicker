package com.karen.photopicker.models.favorite_link;

import java.io.Serializable;

public class Favorite implements Serializable {
    private String link, id;

    public Favorite(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
