package com.karen.photopicker.ui.fragments.gallery;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

interface GalleryFragmentContract {
    interface View extends MvpView {
        void showDialog();
    }

    interface Presenter extends MvpPresenter<View> {

    }
}
