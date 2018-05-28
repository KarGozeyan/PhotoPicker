package com.karen.photopicker.ui.activities.home;

import android.support.v4.app.Fragment;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

interface HomeActivityContract {
    interface View extends MvpView{
        void openFragment(Fragment fragment);
    }

    interface Presenter extends MvpPresenter<View> {

    }
}
