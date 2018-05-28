package com.karen.photopicker.ui.activities.intro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.CallbackManager;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.karen.photopicker.R;
import com.karen.photopicker.adapter.view_pager.IntroViewPagerAdapter;
import com.karen.photopicker.models.intro_item.IntroItem;
import com.karen.photopicker.ui.activities.home.HomeActivity;
import com.karen.photopicker.utils.FacebookUtils;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class IntroActivity extends MvpActivity<IntroActivityContract.View, IntroActivityContract.Presenter>
        implements IntroActivityContract.View, View.OnClickListener {
    private ViewPager viewPager;
    private RelativeLayout button;
    private CircleIndicator indicator;
    private CallbackManager callbackManager;

    @NonNull
    @Override
    public IntroActivityContract.Presenter createPresenter() {
        return new IntroPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initViews();
        callbackManager = CallbackManager.Factory.create();
        presenter.checkIsLoggedIn();
    }

    private void initViews() {
        viewPager = findViewById(R.id.intro_fragment_pager);
        button = findViewById(R.id.intro_fragment_fb_btn);
        indicator = findViewById(R.id.intro_indicator);
    }

    private List<IntroItem> getList() {
        List<IntroItem> items = new ArrayList<>();
        items.add(new IntroItem("Text1", R.drawable.cards_heart));
        items.add(new IntroItem("Text2", R.drawable.card_heart_selected));
        items.add(new IntroItem("Text3", R.drawable.cards_heart));
        items.add(new IntroItem("Text4", R.drawable.card_heart_selected));
        return items;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showIntro() {
        viewPager.setAdapter(new IntroViewPagerAdapter(this, getList()));
        indicator.setViewPager(viewPager);
        button.setOnClickListener(this);
    }

    @Override
    public void skip() {
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onClick(View v) {
        FacebookUtils.login(callbackManager, this);
    }
}
