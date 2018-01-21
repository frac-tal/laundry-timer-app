package com.example.android.washingmachinetimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlanTimesTableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_times_table);
        LayoutInflater inflater = getLayoutInflater();

        LinearLayout plansList = findViewById(R.id.plans_list);
        String[] seperated = new String[2];
        String[] plans = getResources().getStringArray(R.array.plans);
        for (int i=0; i<plans.length; i++) {
            seperated = plans[i].split("\\|", 2);
            LinearLayout entry = (LinearLayout) inflater.inflate(R.layout.plan_entry_layout, null);
            TextView planNameTextView = entry.findViewById(R.id.plan_name);
            TextView planTimeTextView = entry.findViewById(R.id.plan_time);
            planTimeTextView.setText(String.format(getString(R.string.plan_time_entry), seperated[0]));
            planNameTextView.setText(seperated[1]);
            plansList.addView(entry);
        }
    }
}
