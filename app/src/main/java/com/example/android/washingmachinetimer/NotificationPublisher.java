package com.example.android.washingmachinetimer;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by tal on 1/17/18.
 */

public class NotificationPublisher extends BroadcastReceiver
{
    public static final String TAG = "NotificationPublisher";
    public static final String EXTRA_PLAN_NAME = "planNameForNotification";
    public static final String EXTRA_NOTIFICATION_ID = "notificationID";

    int notificationID;
    String planName;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // Your code to execute when the alarm triggers
        // and the broadcast is received.
        Log.v(TAG, "got to the NotificationPublisher!");
        notificationID = intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0);
        planName = intent.getStringExtra(EXTRA_PLAN_NAME);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.laundry_icon)
                        .setContentTitle(context.getString(R.string.done_notification_title))
                        .setContentText(String.format(
                                context.getString(R.string.done_notification_content), planName))
                        //.setSound()
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setVibrate(new long[] {100, 50, 100, 50, 100, 50, 100, 50, 100, 75, 100,
                                75, 150, 80, 150, 90, 150, 100, 300, 110, 300, 120, 300, 130, 350, 150  })
                        .setDefaults(NotificationCompat.DEFAULT_SOUND);
        // Sets an ID for the notification
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(notificationID, mBuilder.build());

    }
}
