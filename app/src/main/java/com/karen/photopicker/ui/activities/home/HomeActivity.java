package com.karen.photopicker.ui.activities.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.karen.photopicker.R;
import com.karen.photopicker.behaviour.BottomNavigationViewBehaviour;
import com.karen.photopicker.ui.fragments.favorite.FavoriteFragment;
import com.karen.photopicker.ui.fragments.gallery.GalleryFragment;
import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;
import com.pinterest.android.pdk.PDKException;
import com.pinterest.android.pdk.PDKResponse;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends MvpActivity<HomeActivityContract.View, HomeActivityContract.Presenter>
        implements HomeActivityContract.View, BottomNavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private BottomNavigationView bnv;
    private FloatingActionButton fab;

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
        PDKClient.configureInstance(this, getString(R.string.app_pinterest_id));
        PDKClient.getInstance().onConnect(this);
        login();
        setSupportActionBar(toolbar);
        bnv.setOnNavigationItemSelectedListener(this);
        bnv.setSelectedItemId(R.id.bnv_menu_albums);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bnv.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehaviour());
    }

    private void initViews() {
        toolbar = findViewById(R.id.home_activity_toolbar);
        bnv = findViewById(R.id.home_activity_bnv);
        fab = findViewById(R.id.home_activity_fab);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bnv_menu_albums:
                openFragment(new GalleryFragment());
                return true;
            case R.id.bnv_menu_favorite:
                openFragment(new FavoriteFragment());
                return true;
        }
        return false;
    }

    @Override
    public void openFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_activity_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void fabAction(View.OnClickListener onClickListener) {
        fab.setOnClickListener(onClickListener);
    }

    private Fragment getFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.home_activity_container);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PDKClient.getInstance().onOauthResponse(requestCode, resultCode, data);
    }

    private void login(){
        List scopes = new ArrayList<String>();
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_READ_PUBLIC);
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_WRITE_PUBLIC);

        PDKClient.getInstance().login(this,scopes,new PDKCallback(){
            @Override
            public void onSuccess(PDKResponse response) {
                Log.e(getClass().getName(), response.getUser().getFirstName());
                //user logged in, use response.getUser() to get PDKUser object
            }

            @Override
            public void onFailure(PDKException exception) {
                Log.e(getClass().getName(), exception.getDetailMessage());
            }
        });
    }
}
