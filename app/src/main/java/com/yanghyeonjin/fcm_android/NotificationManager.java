package com.yanghyeonjin.fcm_android;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

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
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            body = remoteMessage.getNotification().getBody();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(getSmallIcon())
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setChannelId(CHANNEL_ID)
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

            getManager(context).notify(NOTIFY_ID, notificationBuilder.build());
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "")
                    .setSmallIcon(getSmallIcon())
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

            getManager(context).notify(NOTIFY_ID, notificationBuilder.build());
        }
    }

    private static int getSmallIcon() {
        return R.drawable.ic_notification;
    }
}
