package com.karen.photopicker.adapter.recycler_view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.karen.photopicker.R;
import com.karen.photopicker.models.link.FavoriteStorage;
import com.karen.photopicker.models.link.Link;
import com.karen.photopicker.models.link.LinkStorage;
import com.karen.photopicker.ui.fragments.gallery.PhotoAdapterMethods;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {
    private Context context;
    private List<Link> links;
    private PhotoAdapterMethods methods;

    public PhotoAdapter(Context context, List<Link> links, PhotoAdapterMethods methods) {
        this.context = context;
        this.links = links;
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
        methods.loadImages(link.getUrl(), holder.photo);
        methods.setCheck(holder.like, link.isFavorite());
        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methods.fullScreen(holder.getAdapterPosition(),links);
            }
        });
        holder.like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                methods.cache(links, favList);
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/png");
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    private int findItemBtUrl(String url, List<Link> list) {
        int pos = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUrl().equals(url)) {
                pos = i;
            }
        }
        return pos;
    }

    class PhotoHolder extends RecyclerView.ViewHolder {
        private ImageView photo, share;
        private CheckBox like;

        PhotoHolder(View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.photo);
            like = itemView.findViewById(R.id.like);
            share = itemView.findViewById(R.id.share);
        }
    }
}
