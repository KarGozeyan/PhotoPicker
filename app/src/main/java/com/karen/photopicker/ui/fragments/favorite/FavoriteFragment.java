package com.karen.photopicker.ui.fragments.favorite;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.karen.photopicker.R;
import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;
import com.pinterest.android.pdk.PDKException;
import com.pinterest.android.pdk.PDKPin;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends MvpFragment<FavoriteFragmentContract.View, FavoriteFragmentContract.Presenter>
        implements FavoriteFragmentContract.View {
    public static final String TAG = "FavoriteFragment";

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
        PDKClient.getInstance().getPin("399835273150839155", PDKClient.PDKCLIENT_PERMISSION_READ_PUBLIC, new PDKCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                super.onSuccess(response);
                Log.e(TAG, "onSuccess: " + response.toString());
            }

            @Override
            public void onFailure(PDKException exception) {
                super.onFailure(exception);
                exception.printStackTrace();
            }
        });
    }
}
