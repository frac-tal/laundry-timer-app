package com.example.android.washingmachinetimer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
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
    public static final String EXTRA_TIME_AT_START = "timeAtStart";
    public static final String EXTRA_SELECTED_PLAN_TIME = "selectedPlanTime";
    public static final String EXTRA_END_TIME = "endTime";
    public static final String PICKER_STATE = "pickerState";

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

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(context.getString(R.string.done_notification_title));
        bigTextStyle.bigText(String.format(
                context.getString(R.string.done_notification_content), planName));



        Intent donePlanIntent = new Intent(context, ReminderSetActivity.class);
        donePlanIntent.putExtra(EXTRA_PLAN_NAME, planName);
        donePlanIntent.putExtra(EXTRA_TIME_AT_START, intent.getLongExtra(EXTRA_TIME_AT_START, 0));
        donePlanIntent.putExtra(EXTRA_SELECTED_PLAN_TIME, intent.getLongExtra(EXTRA_SELECTED_PLAN_TIME, 0));
        donePlanIntent.putExtra(EXTRA_END_TIME, intent.getLongExtra(EXTRA_END_TIME, 0));

        // thanks to the stackBuilder, when going to this activity by pressing the notification,
        // this activity exists with a natural backstack. without the builder you got 2 of those
        // activities, so you had to press "Done" Twice, or if the app is dead just this activity
        // without the main activity to appear after pressing "Done"
        android.support.v4.app.TaskStackBuilder stackBuilder = android.support.v4.app.TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(donePlanIntent);
        PendingIntent donePlanPendingIntent = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.laundry_icon)
                        .setStyle(bigTextStyle)
                        .setContentIntent(donePlanPendingIntent)
                        .setColor(ContextCompat.getColor(context, R.color.primaryTextColor))
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
