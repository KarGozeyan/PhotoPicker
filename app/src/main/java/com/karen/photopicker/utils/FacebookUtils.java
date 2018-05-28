package com.karen.photopicker.utils;


import android.app.Activity;
import android.support.v4.app.Fragment;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.karen.photopicker.R;
import com.karen.photopicker.ui.fragments.album.AlbumFragment;

import java.util.Collections;

public class FacebookUtils {
    public static void login(CallbackManager callbackManager, Activity activity) {
        LoginManager loginManager = createLoginManager(callbackManager);
        loginManager.logInWithReadPermissions(activity, Collections.singleton("public_profile"));
    }

    private static LoginManager createLoginManager(CallbackManager callbackManager) {
        final LoginManager loginManager = LoginManager.getInstance();
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken.setCurrentAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
            }
        });
        return loginManager;
    }

}
