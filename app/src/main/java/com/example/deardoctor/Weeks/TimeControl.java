package com.example.deardoctor.Weeks;

import android.util.Log;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.util.ArrayList;

public class TimeControl {

    public static void disable(TimePickerDialog timePickerDialog,int fromTime,int fromMinute, int toTime,int toMinutes){

        ArrayList<Timepoint> timepoints = new ArrayList<>();
        DisableTimes.disableTime(fromTime,fromMinute,toTime,toMinutes,timepoints);
        Timepoint [] stockArr = new Timepoint[timepoints.size()];
        for (int i=0;i<timepoints.size();i++){
            stockArr[i] = new Timepoint(timepoints.get(i).getHour(),timepoints.get(i).getMinute());

        }
        timePickerDialog.setDisabledTimes(stockArr);
    }
}
