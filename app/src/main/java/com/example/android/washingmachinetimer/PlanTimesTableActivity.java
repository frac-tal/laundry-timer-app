package com.example.android.washingmachinetimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PlanTimesTableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_times_table);

        TableLayout plansList = findViewById(R.id.plans_list);
        String[] seperated;
        String[] plans = getResources().getStringArray(R.array.plans);
        int stdPadding = (int) getResources().getDimension(R.dimen.standard);
        int fontSize = (int) getResources().getDimension(R.dimen.plan_list_text_size);

        for (int i=0; i<plans.length; i++) {
            seperated = plans[i].split("\\|", 2);
            TableRow row = new TableRow(this);
            TextView planNameTextView = new TextView(this);
            TextView planTimeTextView = new TextView(this);
            planTimeTextView.setText(String.format(getString(R.string.plan_time_entry), seperated[0]));
            planNameTextView.setText(seperated[1]);

            planNameTextView.setPadding(stdPadding, stdPadding, stdPadding, stdPadding);
            planTimeTextView.setPadding(stdPadding, stdPadding, stdPadding, stdPadding);
            planNameTextView.setTextSize(fontSize);
            planTimeTextView.setTextSize(fontSize);

            row.addView(planNameTextView);
            row.addView(planTimeTextView);
            plansList.addView(row);
        }
    }
}
