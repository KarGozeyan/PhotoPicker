package com.karen.photopicker.ui.fragments.favorite;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.karen.photopicker.R;
import com.karen.photopicker.adapter.recycler_view.FavoriteAdapter;
import com.karen.photopicker.models.favorite_link.Favorite;
import com.karen.photopicker.models.favorite_link.FavoriteStorage;
import com.karen.photopicker.models.link.Link;
import com.karen.photopicker.models.link.LinkStorage;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends MvpFragment<FavoriteFragmentContract.View, FavoriteFragmentContract.Presenter>
        implements FavoriteFragmentContract.View, FavAdapterMethods {
    private FavoriteAdapter adapter;
    private RecyclerView rv;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public FavoriteFragmentContract.Presenter createPresenter() {
        return new FavoriteFragmentPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        List<Favorite> favList = FavoriteStorage.restore().getLinks();
        adapter = new FavoriteAdapter(favList,this);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv.setAdapter(adapter);
    }

    private void initViews(View view) {
        rv = view.findViewById(R.id.favorite_rv);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadImages(String url, ImageView target) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.image_placeholder)
                .into(target);
    }
}
