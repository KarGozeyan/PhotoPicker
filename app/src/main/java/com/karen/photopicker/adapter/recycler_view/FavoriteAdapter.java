package com.karen.photopicker.adapter.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.karen.photopicker.ui.fragments.favorite.FavAdapterMethods;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {
    private List<String> links;
    private FavAdapterMethods methods;

    public FavoriteAdapter(List<String> links, FavAdapterMethods methods) {
        this.methods = methods;
        this.links = links;
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteHolder holder, int position) {
        final String link = links.get(position);
        methods.loadImages(link, holder.photo);
        holder.like.setChecked(true);
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.removeFromFavorites(link, holder.getAdapterPosition());
            }
        });
        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.fullscreen(holder.getAdapterPosition(), links);
            }
        });
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    class FavoriteHolder extends RecyclerView.ViewHolder {
        private ImageView photo, share;
        private CheckBox like;

        FavoriteHolder(View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.photo);
            like = itemView.findViewById(R.id.like);
            like.setChecked(true);
            share = itemView.findViewById(R.id.share);
        }
    }
}
