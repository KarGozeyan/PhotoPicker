package com.karen.photopicker.ui.fragments.album;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karen.photopicker.R;
import com.karen.photopicker.utils.GraphUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment implements View.OnClickListener{
    private FloatingActionButton fab;
    private RecyclerView rv;

    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        fab.setOnClickListener(this);
    }

    private void initViews(View view) {
        fab = view.findViewById(R.id.album_fragment_fab);
        rv = view.findViewById(R.id.album_fragment_rv);
    }

    @Override
    public void onClick(View v) {
        GraphUtils.getPhotos("");
    }
}
