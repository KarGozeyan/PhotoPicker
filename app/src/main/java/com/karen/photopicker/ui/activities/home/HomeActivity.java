package com.karen.photopicker.ui.activities.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.karen.photopicker.R;
import com.karen.photopicker.fragment_utils.FragmentTag;
import com.karen.photopicker.fragment_utils.FragmentUtils;
import com.karen.photopicker.ui.fragments.favorite.FavoriteFragment;
import com.karen.photopicker.ui.fragments.gallery.GalleryFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends MvpActivity<HomeActivityContract.View, HomeActivityContract.Presenter>
        implements HomeActivityContract.View, BottomNavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private BottomNavigationView bnv;
    private HashMap<FragmentTag, Fragment> fragmentMap;

    @NonNull
    @Override
    public HomeActivityContract.Presenter createPresenter() {
        return new HomeActivityPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentMap = new HashMap<>();
        initViews();
        setSupportActionBar(toolbar);
        bnv.setOnNavigationItemSelectedListener(this);
        bnv.setSelectedItemId(R.id.bnv_menu_albums);
    }

    private void initViews() {
        toolbar = findViewById(R.id.home_activity_toolbar);
        bnv = findViewById(R.id.home_activity_bnv);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bnv_menu_albums:
                FragmentUtils.showSelectedFragment(new GalleryFragment(),FragmentTag.GALLERY,getSupportFragmentManager(),fragmentMap);
                return true;
            case R.id.bnv_menu_favorite:
                FragmentUtils.showSelectedFragment(new FavoriteFragment(),FragmentTag.FAVORITE,getSupportFragmentManager(),fragmentMap);
                return true;
        }
        return false;
    }
}
