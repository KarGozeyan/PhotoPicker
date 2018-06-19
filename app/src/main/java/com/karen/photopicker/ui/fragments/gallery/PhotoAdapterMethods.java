package com.karen.photopicker.ui.fragments.gallery;

import android.widget.CheckBox;

import com.karen.photopicker.models.favorite_link.Favorite;
import com.karen.photopicker.models.link.Link;

import java.util.List;

public interface PhotoAdapterMethods {
    boolean contains(String url, List<Favorite> list);

    void addToFavorite(List<Favorite> favList, Link link, boolean isChecked);

    void removeFromFavorite(List<Favorite> favList, int position);

    void setCheck(CheckBox check, boolean isFavorite);

    int indexOfCurrentItem(String url,List<Favorite> favList);
}
