package com.karen.photopicker.ui.activities.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.karen.photopicker.R;
import com.karen.photopicker.ui.fragments.album.AlbumFragment;

import static android.view.View.VISIBLE;

public class HomeActivity extends MvpActivity<HomeActivityContract.View, HomeActivityContract.Presenter>
        implements HomeActivityContract.View, BottomNavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private BottomNavigationView bnv;

    @NonNull
    @Override
    public HomeActivityContract.Presenter createPresenter() {
        return new HomeActivityPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        bnv.setVisibility(VISIBLE);
        toolbar.setVisibility(VISIBLE);
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
                openFragment(new AlbumFragment());
                return true;
            case R.id.bnv_menu_favorite:
                //
                return true;
        }
        return false;
    }

    @Override
    public void openFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_activity_container, fragment)
                .commit();
    }
}
