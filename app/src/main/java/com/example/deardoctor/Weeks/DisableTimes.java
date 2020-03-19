package com.example.deardoctor.Weeks;

import android.annotation.SuppressLint;
import android.util.Log;

import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

public class DisableTimes {


    public static void disableTime(int startHour ,int startMinutes, int endHour ,int endMinutes, ArrayList<Timepoint>  timepoints){
        String[] parts = null;
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,startHour);
        calendar.set(Calendar.MINUTE,startMinutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Object[] arrauy = timepoints.toArray();
        final Calendar calendars = Calendar.getInstance();
        calendars.set(Calendar.HOUR_OF_DAY, endHour);
        calendars.set(Calendar.MINUTE,endMinutes);
        calendars.set(Calendar.SECOND,0);
        calendars.set(Calendar.MILLISECOND, 0);
        while (!getFromTime(calendar).equals(getToTIme(calendars)))  {
            parts = getInterval(calendar).split("\\.");
            Log.i("TAG","Time Split" + parts[0]+"  "+parts[1]);
            timepoints.add(new Timepoint(Integer.valueOf(Objects.requireNonNull(parts)[0].trim()), Integer.valueOf(parts[1].trim())));
        }
    }
    private static String getToTIme(final Calendar calendar) {
        @SuppressLint("DefaultLocale") final String interval = String.format(
                "%d.%02d",
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)
        );
        return interval;
    }
    private static String getFromTime(final Calendar calendar) {
        @SuppressLint("DefaultLocale") final String interval = String.format(
                "%d.%02d",
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)
        );
        return interval;
    }
    private static String getInterval(final Calendar calendar) {
        @SuppressLint("DefaultLocale") final String interval = String.format(
                "%d.%02d",
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)
        );
        calendar.add(Calendar.MINUTE, 1);
        return interval;
    }
}
