package com.example.android.BluetoothChat;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

public class MyLeanCloudApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"wNFEPTzS31eQBL13dBD9Jxlm-9Nh9j0Va","yT2aUTfKAPPstDcvfGFbjlxN");
    }
}
