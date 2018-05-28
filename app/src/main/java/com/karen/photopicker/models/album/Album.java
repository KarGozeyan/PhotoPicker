package com.karen.photopicker.models.album;

public class Album {
    private String id, coverPhotoId;
    private int count;

    public Album(String id, String coverPhotoId, int count) {
        this.id = id;
        this.coverPhotoId = coverPhotoId;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public String getCoverPhotoId() {
        return coverPhotoId;
    }

    public int getCount() {
        return count;
    }
}
