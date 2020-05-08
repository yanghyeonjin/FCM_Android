package com.yanghyeonjin.fcm_android;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.RemoteMessage;

public class NotificationManager {
    private static final int NOTIFY_ID = 9999;
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final String CHANNEL_NAME = "CHANNEL_NAME";
    private static final String CHANNEL_DESCRIPTION = "CHANNEL_DESCRIPTION";
    private static final String TAG = "NotificationManager";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createChannel(Context context) {
        NotificationChannel channelMessage = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, android.app.NotificationManager.IMPORTANCE_HIGH);
        channelMessage.setDescription(CHANNEL_DESCRIPTION);
        channelMessage.enableLights(true);
        channelMessage.enableVibration(true);
        channelMessage.setShowBadge(false);
        channelMessage.setLightColor(Color.GREEN);
        channelMessage.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        channelMessage.setVibrationPattern(new long[]{100, 200, 100, 200});

        getManager(context).createNotificationChannel(channelMessage);
    }

    private static android.app.NotificationManager getManager(Context context) {
        return (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static void sendNotification(Context context, RemoteMessage remoteMessage) {
        String title = "";
        String body = "";
        Intent resultIntent = null;
        Uri defaultNotificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // 알림음

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            String destination = remoteMessage.getData().get("destination");
            title = remoteMessage.getData().get("title");
            body = remoteMessage.getData().get("body");

            // Create an Intent for the activity you want to start
            if (destination != null) {
                switch (destination) {
                    case "page1":
                        resultIntent = new Intent(context, Page1Activity.class);
                        break;
                    case "page2":
                        resultIntent = new Intent(context, Page2Activity.class);
                        break;
                    case "page3":
                        resultIntent = new Intent(context, Page3Activity.class);
                        break;
                }
            }
        }

        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);



        // 포그라운드 알림에 대한 설정...
        // 백그라운드 알림은 Node Server 쪽에서 설정해주는 것 같음. -> data를 통해 모든 값을 전달함으로써 해결
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setChannelId(CHANNEL_ID)
                    .setSmallIcon(getSmallIcon())
                    .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                    .setContentTitle(title)
                    .setContentText(body)
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(true)
                    .setSound(defaultNotificationSound);

            getManager(context).notify(NOTIFY_ID, notificationBuilder.build());
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "")
                    .setSmallIcon(getSmallIcon())
                    .setColor(context.getResources().getColor(R.color.colorAccent))
                    .setContentTitle(title)
                    .setContentText(body)
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(true)
                    .setSound(defaultNotificationSound);

            getManager(context).notify(NOTIFY_ID, notificationBuilder.build());
        }
    }

    private static int getSmallIcon() {
        return R.drawable.ic_notification;
    }
}
