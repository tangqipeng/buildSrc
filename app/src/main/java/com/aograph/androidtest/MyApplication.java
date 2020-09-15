package com.aograph.androidtest;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.init(this, "5b88f2ecf43e480adb00023e", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
    }
}
