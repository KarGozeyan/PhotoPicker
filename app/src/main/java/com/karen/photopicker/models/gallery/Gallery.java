package com.karen.photopicker.models.gallery;

public class Gallery {
    private String name, coverPhotoUrl;
    private int count;

    public Gallery(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoverPhotoUrl(String coverPhotoUrl) {
        this.coverPhotoUrl = coverPhotoUrl;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public String getCoverPhotoUrl() {
        return coverPhotoUrl;
    }

    public int getCount() {
        return count;
    }

    public String getFullName() {
        return name + "(" + count + ")";
    }
}
