package com.example.android.washingmachinetimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";

    Button mButton;
    Long timeAtStart;
    NumberPicker planPicker;
    String[] planNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        planPicker = findViewById(R.id.plan_picker);
        planPicker.setMinValue(0);
        planPicker.setMaxValue(planNames.length - 1);
        planPicker.setDisplayedValues(planNames);

        mButton = findViewById(R.id.only_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeAtStart = new GregorianCalendar().getTimeInMillis();
                Context mContext = getApplicationContext();
                int timeToWait = 1000*10;
                Intent intentAlarm = new Intent(mContext, AlarmReceiver.class);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, (timeAtStart + timeToWait), PendingIntent.getBroadcast
                        (mContext, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
            }
        });
    }
}
