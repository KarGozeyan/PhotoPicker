package com.karen.photopicker.ui.activities.intro;

import android.support.annotation.NonNull;

import com.facebook.AccessToken;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

public class IntroPresenter extends MvpBasePresenter<IntroActivityContract.View>
        implements IntroActivityContract.Presenter {
    @Override
    public void checkIsLoggedIn() {
        ifViewAttached(new ViewAction<IntroActivityContract.View>() {
            @Override
            public void run(@NonNull IntroActivityContract.View view) {
                if (AccessToken.getCurrentAccessToken() == null) {
                    view.showIntro();
                } else {
                    view.skip();
                }
            }
        });
    }
}
