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
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.karen.photopicker.R;
import com.karen.photopicker.adapter.recycler_view.FavoriteAdapter;
import com.karen.photopicker.models.link.FavoriteStorage;
import com.karen.photopicker.models.link.Link;
import com.karen.photopicker.models.link.LinkStorage;
import com.karen.photopicker.ui.fragments.full_screeen.FullScreen;
import com.karen.photopicker.ui.fragments.gallery.GalleryFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends MvpFragment<FavoriteFragmentContract.View, FavoriteFragmentContract.Presenter>
        implements FavoriteFragmentContract.View, FavAdapterMethods {
    private FavoriteAdapter adapter;
    private RecyclerView rv;
    private List<String> links = new ArrayList<>();

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

        if (FavoriteStorage.restore() != null && FavoriteStorage.restore().getLinks() != null) {
            links = FavoriteStorage.restore().getLinks();
        }
        adapter = new FavoriteAdapter(links, this);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv.setAdapter(adapter);
        Log.e(getClass().getSimpleName(), "onViewCreated: ");
    }

    private void initViews(View view) {
        rv = view.findViewById(R.id.favorite_rv);
    }

    @Override
    public void loadImages(String url, ImageView target) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.image_placeholder)
                .into(target);
    }

    private List<Link> filterFavorites(List<Link> list) {
        List<Link> favList = new ArrayList<>();
        for (Link link : list)
            if (link.isFavorite()) {
                Toast.makeText(getContext(), link.getUrl(), Toast.LENGTH_SHORT).show();
                favList.add(link);
            }
        return favList;
    }

    @Override
    public Link getItemByUrl(String url, List<Link> list) {
        for (Link link : list) {
            if (url.equals(link.getUrl()))
                return link;
        }
        return null;
    }

    @Override
    public void removeFromFavorites(String link, int position) {
        List<Link> allLinks = new ArrayList<>();
        if (LinkStorage.restore() != null && LinkStorage.restore().getLinks() != null) {
            allLinks = LinkStorage.restore().getLinks();
        }
        Link item = getItemByUrl(link, allLinks);
        if (item != null && allLinks.contains(item)) {
            item.setFavorite(false);
            int itemPos = allLinks.indexOf(item);
            allLinks.set(itemPos, item);
        }
        links.remove(position);
        cache(allLinks, links, position);
    }

    @Override
    public void cache(List<Link> links, List<String> favorites, int pos) {
        adapter.notifyItemRemoved(pos);
        new LinkStorage(links);
        new FavoriteStorage(favorites);
    }

    @Override
    public void fullscreen(int position, List<String> links) {
        List<Link> newList = new ArrayList<>();
        for (String link : links) {
            Link newLink = new Link(link);
            newLink.setFavorite(true);
            newList.add(newLink);
        }
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_activity_container, FullScreen.newInstance(position, (ArrayList) newList))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

}
