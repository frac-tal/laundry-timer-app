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
    int[] planTimes;
    int selectedPlanTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        planNames = getResources().getStringArray(R.array.plans);
        planTimes = new int[planNames.length];
        for (int i=0; i<planNames.length; i++) {
            String[] sepperate = planNames[i].split("\\|", 2);
            //planTimes[i] = Integer.parseInt(sepperate[0]) * 1000 * 60;
            planTimes[i] = Integer.parseInt(sepperate[0]) * 1000; // just for now, waiting seconds instead of minutes.
            planNames[i] = sepperate[1];
        }

        planPicker = findViewById(R.id.plan_picker);
        planPicker.setMinValue(0);
        planPicker.setMaxValue(planNames.length - 1);
        planPicker.setDisplayedValues(planNames);

        planPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                selectedPlanTime = planTimes[newVal];
            }
        });

        mButton = findViewById(R.id.only_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeAtStart = new GregorianCalendar().getTimeInMillis();
                Context mContext = getApplicationContext();
                Intent intentAlarm = new Intent(mContext, AlarmReceiver.class);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, (timeAtStart + selectedPlanTime), PendingIntent.getBroadcast
                        (mContext, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
            }
        });
    }
}
