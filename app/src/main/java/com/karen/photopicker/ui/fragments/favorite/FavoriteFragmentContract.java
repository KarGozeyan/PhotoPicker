package com.karen.photopicker.ui.fragments.favorite;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

interface FavoriteFragmentContract {
    interface View extends MvpView {
        void updateAdapter();
    }

    interface Presenter extends MvpPresenter<View> {

    }
}
