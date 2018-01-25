package com.example.android.washingmachinetimer;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";
    public static final String EXTRA_PLAN_NAME = "planNameForNotification";
    public static final String EXTRA_NOTIFICATION_ID = "notificationID";
    public static final String EXTRA_TIME_AT_START = "timeAtStart";
    public static final String EXTRA_SELECTED_PLAN_TIME = "selectedPlanTime";
    public static final String EXTRA_END_TIME = "endTime";
    public static final String PICKER_STATE = "pickerState";

    Button mButton;
    Long timeAtStart;
    NumberPicker planPicker;
    String[] planNames;
    Long[] planTimes;
    Long selectedPlanTime;
    String selectedPlanName;
    int notificationID;
    Long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        planNames = getResources().getStringArray(R.array.plans);
        planTimes = new Long[planNames.length];
        for (int i=0; i<planNames.length; i++) {
            String[] sepperate = planNames[i].split("\\|", 2);
            planTimes[i] = Long.parseLong(sepperate[0], 10) * 1000L * 60;
            //planTimes[i] = Long.parseLong(sepperate[0], 10) * 1000L; // just for now, waiting seconds instead of minutes.
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
                selectedPlanName = planNames[newVal];
                Log.v(TAG, String.format("picker changed to %d", newVal) );
                Log.v(TAG, String.format("selected plan: %s", selectedPlanName));
                Log.v(TAG, String.format("plan time in milliseconds: %d", selectedPlanTime));
            }
        });

        mButton = findViewById(R.id.only_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "received button press");
                timeAtStart = new GregorianCalendar().getTimeInMillis();
                endTime = timeAtStart + selectedPlanTime;
                notificationID = (int) (timeAtStart % Integer.MAX_VALUE);
                Context mContext = getApplicationContext();
                Intent notificationIntent = new Intent(mContext, NotificationPublisher.class);
                notificationIntent.putExtra(EXTRA_PLAN_NAME, selectedPlanName);
                notificationIntent.putExtra(EXTRA_NOTIFICATION_ID, notificationID);
                // for the activity opened from the notification:
                notificationIntent.putExtra(EXTRA_TIME_AT_START, timeAtStart);
                notificationIntent.putExtra(EXTRA_SELECTED_PLAN_TIME, selectedPlanTime);
                notificationIntent.putExtra(EXTRA_END_TIME, endTime);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, endTime, PendingIntent.getBroadcast
                        (mContext, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT));
                Intent afterSetIntent = new Intent(mContext, ReminderSetActivity.class);
                afterSetIntent.putExtra(EXTRA_PLAN_NAME, selectedPlanName);
                afterSetIntent.putExtra(EXTRA_TIME_AT_START, timeAtStart);
                afterSetIntent.putExtra(EXTRA_SELECTED_PLAN_TIME, selectedPlanTime);
                afterSetIntent.putExtra(EXTRA_END_TIME, endTime);
                startActivity(afterSetIntent);
            }
        });
        if (savedInstanceState != null) {
            int pickerState = savedInstanceState.getInt(PICKER_STATE);
            planPicker.setValue(pickerState);
            selectedPlanTime = planTimes[pickerState];
            selectedPlanName = planNames[pickerState];
        } else {
            selectedPlanTime = planTimes[0];
            selectedPlanName = planNames[0];
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's instance
        outState.putInt(PICKER_STATE, planPicker.getValue());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_times_table:
                Log.v(TAG, "got info request");
                Intent planTableIntent = new Intent(this, PlanTimesTableActivity.class);
                startActivity(planTableIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.primaryTextColor), PorterDuff.Mode.SRC_ATOP);
            }
        }

        return true;
    }

}
