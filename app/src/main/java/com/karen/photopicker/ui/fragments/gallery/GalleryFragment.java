package com.karen.photopicker.ui.fragments.gallery;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.ArcMotion;
import android.support.transition.ChangeBounds;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.karen.photopicker.R;
import com.karen.photopicker.adapter.recycler_view.PhotoAdapter;
import com.karen.photopicker.flickr.FlickrFetchr;
import com.karen.photopicker.models.link.FavoriteStorage;
import com.karen.photopicker.models.link.Link;
import com.karen.photopicker.models.link.LinkStorage;
import com.karen.photopicker.ui.fragments.favorite.FavoriteFragment;
import com.karen.photopicker.ui.fragments.full_screeen.FullScreen;
import com.karen.photopicker.utils.ConnectionUtils;
import com.karen.photopicker.utils.fragment_utils.FragmentTag;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends MvpFragment<GalleryFragmentContract.View, GalleryFragmentContract.Presenter>
        implements GalleryFragmentContract.View, PhotoAdapterMethods {
    private RecyclerView rv;
    private PhotoAdapter adapter;

    private class FetchItemsTask extends AsyncTask<Void, Void, List<Link>> {
        @Override
        protected List<Link> doInBackground(Void... voids) {
            return new FlickrFetchr().fetchItems();
        }

        @Override
        protected void onPostExecute(List<Link> linkList) {
            Log.e(getClass().getSimpleName(), "NewList Size:" + linkList.size());
            List<Link> cachedLinks = new ArrayList<>();
            if (LinkStorage.restore() != null && LinkStorage.restore().getLinks() != null) {
                cachedLinks = LinkStorage.restore().getLinks();
            }
            linkList.addAll(cachedLinks);
            setUpRecyclerView(linkList);
            new LinkStorage(linkList);
            Log.e(getClass().getSimpleName(), "NewList Size:" + linkList.size());
            Log.e(getClass().getSimpleName(), "Cache Size:" + LinkStorage.restore().getLinks().size());
        }

        private List<String> filterLinks(List<Link> list) {
            List<String> urls = new ArrayList<>();
            for (Link link : list)
                urls.add(link.getUrl());
            return urls;
        }
    }

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public GalleryFragmentContract.Presenter createPresenter() {
        return new GalleryFragmentPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        new FetchItemsTask().execute();
    }

    private void initViews(View view) {
        rv = view.findViewById(R.id.album_fragment_rv);
    }

    private void setUpRecyclerView(List<Link> linkList) {
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new PhotoAdapter(getContext(), linkList, this);
        rv.setAdapter(adapter);
    }

    @Override
    public void setCheck(CheckBox check, boolean isFavorite) {
        check.setChecked(isFavorite);
    }

    @Override
    public void fullScreen(int position, List<Link> list) {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.home_activity_container, FullScreen.newInstance(position,list))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onHiddenChanged(boolean hidden) { rv.stopScroll(); }

    @Override
    public void loadImages(String url, ImageView target) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.image_placeholder)
                .into(target);
    }

    @Override
    public void cache(List<Link> links, List<String> favorites) {
        new LinkStorage(links);
        new FavoriteStorage(favorites);
        if (getActivity().getSupportFragmentManager().findFragmentByTag("Favorite") != null) {
            FavoriteFragment fragment = (FavoriteFragment) getActivity().getSupportFragmentManager().findFragmentByTag("Favorite");
            fragment.updateAdapter();
        }
    }

    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }
}