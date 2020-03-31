package com.example.chatdemo;

import android.app.Application;

import com.example.chatdemo.data.local.AppSharedPrefManager;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppSharedPrefManager.initInstance(MyApp.this);
    }
}
