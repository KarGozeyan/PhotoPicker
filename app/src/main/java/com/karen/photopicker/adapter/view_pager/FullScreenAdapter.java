package com.karen.photopicker.adapter.view_pager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.karen.photopicker.R;
import com.karen.photopicker.models.link.FavoriteStorage;
import com.karen.photopicker.models.link.Link;
import com.karen.photopicker.models.link.LinkStorage;
import com.karen.photopicker.ui.fragments.full_screeen.FullScreenMethods;

import java.util.ArrayList;
import java.util.List;

public class FullScreenAdapter extends PagerAdapter {
    private final FullScreenMethods methods;
    private Context context;
    private List<Link> links;

    public FullScreenAdapter(Context context, List<Link> links, FullScreenMethods methods) {
        this.context = context;
        this.links = links;
        this.methods = methods;
    }

    @Override
    public int getCount() {
        return links.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final Link link = links.get(position);

        View layout = LayoutInflater.from(context).inflate(R.layout.full_screen_item, container, false);
        PhotoView photo;
        ImageView share;
        CheckBox like;
        photo = layout.findViewById(R.id.photo_full_screen);
        like = layout.findViewById(R.id.favorite_full_screen);
        share = layout.findViewById(R.id.share_full_screen);
        methods.load(photo, link.getUrl());
        like.setChecked(link.isFavorite());
        like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Link newLink = link;
                newLink.setFavorite(isChecked);
                List<String> favList = new ArrayList<>();
                if (FavoriteStorage.restore() != null && FavoriteStorage.restore().getLinks() != null) {
                    favList = FavoriteStorage.restore().getLinks();
                }
                if (isChecked) {
                    favList.add(link.getUrl());
                } else {
                    favList.remove(link.getUrl());
                }
                links.set(links.indexOf(link), newLink);
                new FavoriteStorage(favList);
                new LinkStorage(links);
            }
        });
        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
