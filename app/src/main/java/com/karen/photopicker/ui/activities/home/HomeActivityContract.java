package com.karen.photopicker.ui.activities.home;

import android.support.v4.app.Fragment;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

interface HomeActivityContract {
    interface View extends MvpView{
        void hideBottomNavigationView();
        void showBottomNavigationView();
        void hideToolbar();
        void showToolbar();
    }

    interface Presenter extends MvpPresenter<View> {

    }
}
