package com.karen.photopicker.ui.fragments.full_screeen;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.karen.photopicker.R;
import com.karen.photopicker.adapter.view_pager.FullScreenAdapter;
import com.karen.photopicker.models.link.Link;
import com.karen.photopicker.models.link.LinkStorage;
import com.karen.photopicker.ui.activities.home.HomeActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FullScreen extends Fragment implements FullScreenMethods {
    private static final String KEY_POSITION = "POSITION";

    public FullScreen() {
        // Required empty public constructor
    }

    public static FullScreen newInstance(int position,List<Link> obj) {

        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        args.putSerializable("KEY", (Serializable) obj);
        FullScreen fragment = new FullScreen();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).hideBottomNavigationView();
        ((HomeActivity) getActivity()).hideToolbar();
        List<Link> links = LinkStorage.restore().getLinks();
        ViewPager pager = view.findViewById(R.id.full_screen_pager);
        pager.setAdapter(new FullScreenAdapter(getContext(), links, this));
        pager.setCurrentItem(getArguments().getInt(KEY_POSITION));
        Log.e(getClass().getSimpleName(), "Geted cache Size" + links.size());
    }

    @Override
    public void load(ImageView target, String link) {
        Picasso.get()
                .load(link)
                .into(target);
    }

    @Override
    public void onDestroy() {
        ((HomeActivity) getActivity()).showBottomNavigationView();
        ((HomeActivity) getActivity()).showToolbar();
        super.onDestroy();
    }

    @Override
    public void share() {
        // TODO: 7/3/2018
    }
}
