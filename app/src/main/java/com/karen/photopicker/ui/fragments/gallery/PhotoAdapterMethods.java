package com.karen.photopicker.ui.fragments.gallery;

import android.widget.CheckBox;
import android.widget.ImageView;

import com.karen.photopicker.models.link.Link;

import java.util.List;

public interface PhotoAdapterMethods {
    void setCheck(CheckBox check, boolean isFavorite);

    void fullScreen(int position, List<Link> list);

    void loadImages(String url, ImageView target);

    void cache(List<Link> links, List<String> favorites);
}
