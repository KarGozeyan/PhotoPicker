package com.karen.photopicker.adapter.recycler_view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.karen.photopicker.R;
import com.karen.photopicker.flickr.ThumbnailDownloader;
import com.karen.photopicker.models.favorite_link.Favorite;
import com.karen.photopicker.models.favorite_link.FavoriteStorage;
import com.karen.photopicker.models.link.Link;
import com.karen.photopicker.ui.fragments.gallery.PhotoAdapterMethods;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {
    private Context context;
    private List<Link> links;
    private ThumbnailDownloader downloader;
    private PhotoAdapterMethods methods;

    public PhotoAdapter(Context context, List<Link> links, ThumbnailDownloader downloader, PhotoAdapterMethods methods) {
        this.context = context;
        this.links = links;
        this.downloader = downloader;
        this.methods = methods;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PhotoHolder holder, int position) {
        final Link link = links.get(position);
        methods.setCheck(holder.like, link.isFavorite());
        downloader.queueThumbnail(holder, link.getUrl());
        holder.like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                List<Favorite> favLinks;
                try {
                    favLinks = FavoriteStorage.restore().getLinks();
                } catch (NullPointerException e) {
                    favLinks = new ArrayList<>();
                }
                if (isChecked) {
                    methods.addToFavorite(favLinks, link, isChecked);
                } else {
                    methods.removeFromFavorite(favLinks, methods.indexOfCurrentItem(link.getUrl(),favLinks));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return links.size();
    }


    public class PhotoHolder extends RecyclerView.ViewHolder {
        private ImageView photo, share;
        private CheckBox like;

        PhotoHolder(View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.photo);
            like = itemView.findViewById(R.id.like);
            share = itemView.findViewById(R.id.share);
        }

        public void bindDrawable(String link) {
            Picasso.get()
                    .load(link)
                    .placeholder(R.drawable.image_placeholder)
                    .into(photo);
        }
    }
}
