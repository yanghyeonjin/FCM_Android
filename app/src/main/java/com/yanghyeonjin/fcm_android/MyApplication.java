package com.yanghyeonjin.fcm_android;

import android.app.Application;
import android.os.Build;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /* 오레오 버전부터 Notification Channel 필요 */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager.createChannel(this);
        }
    }
}
