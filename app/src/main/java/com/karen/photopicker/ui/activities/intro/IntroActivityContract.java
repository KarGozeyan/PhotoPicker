package com.karen.photopicker.ui.activities.intro;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

interface IntroActivityContract {
    interface View extends MvpView {
        void showIntro();
        void skip();
    }

    interface Presenter extends MvpPresenter<View> {
        void checkIsLoggedIn();
    }
}
