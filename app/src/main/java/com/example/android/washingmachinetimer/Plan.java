package com.example.android.washingmachinetimer;

/**
 * Created by tal on 1/20/18.
 */

public class Plan {
    public String name; // the name of the plan
    public int time; // how long does it take, in milliseconds

    public Plan (String planName, int planTime) {
        name = planName;
        time = planTime;
    }

    /***
     * @return plan name
     */
    public String getName(){
        return name;
    }


    /***
     * @return plan time in milliseconds
     */
    public int getTime(){
        return time;
    }
}
