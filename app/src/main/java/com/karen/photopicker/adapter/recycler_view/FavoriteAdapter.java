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
import com.karen.photopicker.models.favorite_link.Favorite;
import com.karen.photopicker.models.link.Link;
import com.karen.photopicker.models.link.LinkStorage;
import com.karen.photopicker.ui.fragments.favorite.FavAdapterMethods;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {
    private List<Favorite> links;
    private FavAdapterMethods methods;

    public FavoriteAdapter(List<Favorite> links, FavAdapterMethods methods) {
        this.links = links;
        this.methods = methods;
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteHolder holder, int position) {
        final Favorite favorite = links.get(position);
        methods.loadImages(favorite.getLink(), holder.photo);
        holder.like.setChecked(true);
        holder.like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    links.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    List<Link> list = LinkStorage.restore().getLinks();
                    Link item = getCurrentItem(favorite.getLink(), list);
                    if (item != null){
                        int pos = links.indexOf(item);
                        Log.e("POSITION", "onCheckedChanged: " + pos );
                        item.setFavorite(false);
                        list.set(pos, item);
                        new LinkStorage(list);
                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    private int getIndexOfCurrentItem(String url, List<Link> list) {
        for (int i = 0; i < list.size(); i++) {
            if (url.equals(list.get(i).getUrl())) {
                return i;
            }
        }
        return 0;
    }

    private Link getCurrentItem(String url, List<Link> list) {
        for (Link link : list) {
            if (url.equals(link.getUrl())) {
                return link;
            }
        }
        return null;
    }

    class FavoriteHolder extends RecyclerView.ViewHolder {
        private ImageView photo, share;
        private CheckBox like;

        FavoriteHolder(View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.photo);
            like = itemView.findViewById(R.id.like);
            share = itemView.findViewById(R.id.share);
        }
    }

}
