package com.karen.photopicker;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.LauncherActivity;

public class App extends Application {
    private static App instance = new App();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }
}
