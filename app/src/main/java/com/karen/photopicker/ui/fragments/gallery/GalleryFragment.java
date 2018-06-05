package com.karen.photopicker.ui.fragments.gallery;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.karen.photopicker.R;
import com.karen.photopicker.adapter.recycler_view.GalleryAdapter;
import com.karen.photopicker.models.gallery.Gallery;
import com.karen.photopicker.ui.activities.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends MvpFragment<GalleryFragmentContract.View, GalleryFragmentContract.Presenter>
        implements GalleryFragmentContract.View, View.OnClickListener {

    private RecyclerView rv;
    private List<Gallery> galleryList = new ArrayList<>();
    private GalleryAdapter adapter;
    public static final String TAG = "GalleryFragment";

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
        setFabAction(this);
        adapter = new GalleryAdapter(galleryList);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv.setAdapter(adapter);
    }

    private void initViews(View view) {
        rv = view.findViewById(R.id.album_fragment_rv);
    }

    @Override
    public void onClick(View v) {
        showDialog();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        setFabAction(null);
        super.onDestroy();
    }

    @Override
    public void showDialog() {
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.add_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(layout);
        final EditText input = layout.findViewById(R.id.add_edit_text);
        builder.setTitle("Add Gallery");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                galleryList.add(new Gallery(input.getText().toString()));
                adapter.notifyDataSetChanged();

            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        final Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setEnabled(false);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!input.getText().toString().isEmpty()) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!input.getText().toString().isEmpty()) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!input.getText().toString().isEmpty()) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }
        });
    }

    private void setFabAction(View.OnClickListener onClickListener) {
        ((HomeActivity) getActivity()).fabAction(onClickListener);
    }
}
