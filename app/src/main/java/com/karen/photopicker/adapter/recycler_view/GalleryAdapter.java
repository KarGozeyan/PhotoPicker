package com.karen.photopicker.adapter.recycler_view;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.karen.photopicker.R;
import com.karen.photopicker.models.gallery.Gallery;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryHolder> {
    private List<Gallery> galleries;

    public GalleryAdapter(List<Gallery> galleries) {
        this.galleries = galleries;
    }

    @NonNull
    @Override
    public GalleryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GalleryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryHolder holder, int position) {
        Picasso.get()
                .load("http://i.imgur.com/DvpvklR.png")
                .into(holder.imageView);
        holder.textView.setText(galleries.get(position).getFullName());
    }

    @Override
    public int getItemCount() {
        return galleries.size();
    }

    class GalleryHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        GalleryHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.gallery_item_cp);
            textView = itemView.findViewById(R.id.gallery_item_text);
        }
    }
}
