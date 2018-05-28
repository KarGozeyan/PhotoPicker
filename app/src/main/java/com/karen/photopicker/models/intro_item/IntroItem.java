package com.karen.photopicker.models.intro_item;

import android.support.annotation.DrawableRes;

public class IntroItem {
    private String text;
    private @DrawableRes
    int image;

    public IntroItem(String text, @DrawableRes int image) {
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public int getImage() {
        return image;
    }
}
