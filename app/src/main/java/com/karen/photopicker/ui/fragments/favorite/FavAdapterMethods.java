package com.karen.photopicker.ui.fragments.favorite;

import android.widget.ImageView;

import com.karen.photopicker.models.link.Link;

import java.util.List;

public interface FavAdapterMethods {
    void loadImages(String url, ImageView target);

    void removeFromFavorites(String link, int position);

    Link getItemByUrl(String url, List<Link> list);

    void cache(List<Link> links, List<String> favorites, int pos);
}
