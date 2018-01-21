package com.example.android.washingmachinetimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ReminderSetActivity extends AppCompatActivity {

    public static final String TAG = "ReminderSetActivity";

    public static final String EXTRA_PLAN_NAME = "planNameForNotification";
    public static final String EXTRA_NOTIFICATION_ID = "notificationID";
    public static final String EXTRA_TIME_AT_START = "timeAtStart";
    public static final String EXTRA_SELECTED_PLAN_TIME = "selectedPlanTime";
    public static final String EXTRA_END_TIME = "endTime";

    TextView planNameTextView;
    TextView startTimeTextView;
    TextView countDownTextView;
    Button resetButton;

    String planName;
    // times in milliseconds:
    long timeAtStart;
    long planTime;
    long endTime;

    String startTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_set);

        // get values from intent
        final Intent intent = getIntent();
        planName = intent.getStringExtra(EXTRA_PLAN_NAME);
        timeAtStart = intent.getLongExtra(EXTRA_TIME_AT_START, new GregorianCalendar().getTimeInMillis());
        planTime = intent.getLongExtra(EXTRA_SELECTED_PLAN_TIME, 0);
        endTime = intent.getLongExtra(EXTRA_END_TIME, (timeAtStart + planTime));

        // get UI components by id
        planNameTextView = findViewById(R.id.plan_name_title);
        startTimeTextView = findViewById(R.id.start_time_text_view);
        countDownTextView = findViewById(R.id.countdown_text_view);
        resetButton = findViewById(R.id.reset_button);

        // set values to UI
        planNameTextView.setText(planName);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTimeInMillis(timeAtStart);
        startTime = String.format("%02d:%02d:%02d", startCalendar.get(Calendar.HOUR_OF_DAY),
                startCalendar.get(Calendar.MINUTE), startCalendar.get(Calendar.SECOND));
        startTimeTextView.setText(startTime);


        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Context mContext = getApplicationContext();
                Intent notificationIntent = new Intent(mContext, NotificationPublisher.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        mContext, 1, notificationIntent,     PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(pendingIntent);
                finish();
            }
        });

        long nowMillis = new GregorianCalendar().getTimeInMillis();
        new CountDownTimer(endTime - nowMillis, 1000) {

            public void onTick(long millisUntilFinished) {
                int hours = (int) millisUntilFinished / (1000 * 60 * 60);
                int minutes = (int) (millisUntilFinished % (1000 * 60 * 60)) / (1000 * 60);
                int seconds = (int) (millisUntilFinished % (1000 * 60)) / 1000;
                countDownTextView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            }

            public void onFinish() {
                countDownTextView.setText(R.string.countdown_done);
                resetButton.setText(R.string.reset_button_countdown_done);
            }
        }.start();



    }
}
