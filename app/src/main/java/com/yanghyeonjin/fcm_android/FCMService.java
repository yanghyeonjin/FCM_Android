package com.yanghyeonjin.fcm_android;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMService extends FirebaseMessagingService {
    private static final String TAG = "FCMService";

    @Override
    public void onNewToken(@NonNull String s) {
        /*
         * 새 토큰이 생성될 때마다 onNewToken 콜백이 호출됩니다.
         *
         * 다음과 같은 경우에 등록 토큰이 변경될 수 있습니다.
         * - 앱에서 인스턴스 ID 삭제
         * - 새 기기에서 앱 복원
         * - 사용자가 앱 삭제/재설치
         * - 사용자가 앱 데이터 소거
         */
        Log.e(TAG, "Refreshed token: " + s);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        // sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // 메시지를 받았을 경우, 그 메시지에 대하여 구현하는 부분
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        NotificationManager.sendNotification(getApplicationContext(), remoteMessage);
    }
}
