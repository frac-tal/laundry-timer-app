package com.example.android.washingmachinetimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ReminderSetActivity extends AppCompatActivity {


    TextView startTimeTextView;
    TextView countDownTextView;
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_set);

        startTimeTextView = findViewById(R.id.start_time_text_view);
        countDownTextView = findViewById(R.id.countdown_text_view);
        resetButton = findViewById(R.id.reset_button);
    }
}
