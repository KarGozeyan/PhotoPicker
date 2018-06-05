package com.karen.photopicker.ui.activities.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.karen.photopicker.R;
import com.karen.photopicker.behaviour.BottomNavigationViewBehaviour;
import com.karen.photopicker.fragment_utils.FragmentOpenType;
import com.karen.photopicker.fragment_utils.FragmentTag;
import com.karen.photopicker.fragment_utils.FragmentUtils;
import com.karen.photopicker.models.gallery.Gallery;
import com.karen.photopicker.ui.fragments.favorite.FavoriteFragment;
import com.karen.photopicker.ui.fragments.gallery.GalleryFragment;
import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;
import com.pinterest.android.pdk.PDKException;
import com.pinterest.android.pdk.PDKResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends MvpActivity<HomeActivityContract.View, HomeActivityContract.Presenter>
        implements HomeActivityContract.View, BottomNavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private BottomNavigationView bnv;
    private FloatingActionButton fab;
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
        PDKClient.configureInstance(this, getString(R.string.app_pinterest_id));
        PDKClient.getInstance().onConnect(this);
//        login();
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
                FragmentUtils.showSelectedFragment(new GalleryFragment(),FragmentTag.GALLERY,getSupportFragmentManager(),fragmentMap);
                return true;
            case R.id.bnv_menu_favorite:
                FragmentUtils.showSelectedFragment(new FavoriteFragment(),FragmentTag.FAVORITE,getSupportFragmentManager(),fragmentMap);
                return true;
        }
        return false;
    }

    @Override
    public void openFragment(Fragment fragment) {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.home_activity_container, fragment)
//                .show(fragment)
//                .commit();
    }

    public void fabAction(View.OnClickListener onClickListener) {
        fab.setOnClickListener(onClickListener);
    }

    private Fragment getFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.home_activity_container);
    }

    private void addFragment(Fragment fragment, String tag, boolean isBackStack) {
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(tag);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();

        if (isBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        if (fragmentByTag != null) {
            Log.e(getClass().getName(), "addFragment: 1");
//            fragmentTransaction.hide(fragment);
            if (fragment.isHidden()) {
                fragmentTransaction.show(fragment);
            } else {
                fragmentTransaction.hide(fragment);
            }
        } else {
            Log.e(getClass().getName(), "addFragment: 2");
            fragmentTransaction.add(R.id.home_activity_container, fragment, tag);
        }
        fragmentTransaction.commit();

    }

    private void showGallery() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(new GalleryFragment())
                .hide(new FavoriteFragment())
                .commit();
    }

    private void showFavorite() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(new FavoriteFragment())
                .hide(new GalleryFragment())
                .commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PDKClient.getInstance().onOauthResponse(requestCode, resultCode, data);
    }

    private void login() {
        List scopes = new ArrayList<String>();
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_READ_PUBLIC);
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_WRITE_PUBLIC);

        PDKClient.getInstance().login(this, scopes, new PDKCallback() {
            @Override
            public void onSuccess(PDKResponse response) {
                Log.e(getClass().getName(), response.getUser().getFirstName());
            }

            @Override
            public void onFailure(PDKException exception) {
                Log.e(getClass().getName(), exception.getDetailMessage());
            }
        });
    }
}
