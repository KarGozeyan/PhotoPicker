package com.karen.photopicker.models.photo;

import android.graphics.Bitmap;

public class Photo {
    private Bitmap photo;
    private String id, albumId;

    public Photo(String id, String albumId) {
        this.id = id;
        this.albumId = albumId;
    }

    public Bitmap photo() {
        return photo;
    }

    public String getId() {
        return id;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}