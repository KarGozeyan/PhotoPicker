package com.karen.photopicker.ui.fragments.gallery;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.karen.photopicker.R;
import com.karen.photopicker.adapter.recycler_view.PhotoAdapter;
import com.karen.photopicker.flickr.FlickrFetchr;
import com.karen.photopicker.flickr.ThumbnailDownloader;
import com.karen.photopicker.models.favorite_link.Favorite;
import com.karen.photopicker.models.favorite_link.FavoriteStorage;
import com.karen.photopicker.models.link.Link;
import com.karen.photopicker.models.link.LinkStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends MvpFragment<GalleryFragmentContract.View, GalleryFragmentContract.Presenter>
        implements GalleryFragmentContract.View, PhotoAdapterMethods {
    private SwipeRefreshLayout swipe;
    private RecyclerView rv;
    private PhotoAdapter adapter;
    private ThumbnailDownloader downloader;
    private List<Link> links = new ArrayList<>();

    private class FetchItemsTask extends AsyncTask<Void, Void, List<Link>> {

        @Override
        protected List<Link> doInBackground(Void... voids) {
            return new FlickrFetchr().fetchItems();
        }

        @Override
        protected void onPostExecute(List<Link> linkList) {
            setupRecyclerView(linkList);
            for (Link link : linkList) {
                Log.e("MyTag", "onPostExecute: " + link.getUrl());
            }
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initSwipe();
        runThumbnail();
    }

    private void runThumbnail() {
        new FetchItemsTask().execute();
        Handler responseHandler = new Handler();
        downloader = new ThumbnailDownloader<>(responseHandler);
        downloader.setThumbnailDownloadListener(
                new ThumbnailDownloader.ThumbnailDownloadListener<PhotoAdapter.PhotoHolder>() {
                    @Override
                    public void onThumbnailDownloaded(PhotoAdapter.PhotoHolder target, String link) {
                        if (!LinkStorage.restore().getLinks().contains(new Link(link))) {
                            links.add(new Link(link));
                        }
                        new LinkStorage(links);
                        target.bindDrawable(link);
                    }
                }
        );
        downloader.start();
        downloader.getLooper();
    }

    private void initSwipe() {
        swipe.setColorSchemeColors(Color.parseColor("#FF4081"),
                Color.parseColor("#3F51B5"),
                Color.parseColor("#CDDC39"));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runThumbnail();
                        swipe.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        downloader.clearQueue();
    }

    private void initViews(View view) {
        rv = view.findViewById(R.id.album_fragment_rv);
        swipe = view.findViewById(R.id.photo_swipe);
    }

    private void setupRecyclerView(List<Link> list) {
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new PhotoAdapter(getContext(), list, downloader, this);
        rv.setAdapter(adapter);
    }

    @Override
    public boolean contains(String url, List<Favorite> list) {
        List<String> urls = new ArrayList<>();
        for (Favorite f : list) {
            urls.add(f.getLink());
        }
        return urls.contains(url);
    }

    @Override
    public void removeFromFavorite(List<Favorite> favList, int position) {
        favList.remove(position);
        new FavoriteStorage(favList);
    }

    @Override
    public void addToFavorite(List<Favorite> favLinks, Link link, boolean isChecked) {
        Favorite f = new Favorite(link.getUrl());
        if (isChecked && !contains(link.getUrl(), favLinks)) {
            favLinks.add(f);
            link.setFavorite(true);
        }
        new FavoriteStorage(favLinks);
    }

    @Override
    public void setCheck(CheckBox check, boolean isFavorite) {
        check.setChecked(isFavorite);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public int indexOfCurrentItem(String url, List<Favorite> favList) {
        for (int i = 0; i < favList.size(); i++) {
            if (url.equals(favList.get(i).getLink())) {
                return i;
            }
        }
        return 0;
    }
}