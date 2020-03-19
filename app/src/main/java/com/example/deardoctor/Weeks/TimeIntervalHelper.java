package com.example.deardoctor.Weeks;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.Calendar;

public class TimeIntervalHelper {
   private static boolean halfAnhourIntervals;
    @SuppressLint("WrongConstant")
    public static void generateTimeInterval(final ArrayList<String> intervals, final int startHour,
                                            final int startZone,int endZone, final boolean isStartHalf, int endHour,
                                            final boolean isEndHalf) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, startHour);
        calendar.set(Calendar.MINUTE, isStartHalf ? 30 : 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

//        final Calendar calendars = Calendar.getInstance();
//        calendars.set(Calendar.HOUR,endHour);
//        calendars.set(Calendar.AM_PM, Calendar.PM);
        endHour = endHour == 12 ? 0 : endHour;
        while (calendar.get(Calendar.HOUR_OF_DAY) != endHour)
            intervals.add(getInterval(calendar));
        intervals.add(getInterval(calendar));
        if (isEndHalf)
            intervals.add(getInterval(calendar));
    }
    static void generateTimeInterval(final ArrayList<String> intervals, final int startHour, int endHour) {
        generateTimeInterval(intervals, startHour,1,2, false, endHour, false);
    }

    private static String getInterval(final Calendar calendar) {
        @SuppressLint("DefaultLocale") final String interval = String.format(
                "%d:%02d %s",
                calendar.get(Calendar.HOUR) != 0 ? calendar.get(Calendar.HOUR) : 12 ,
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.AM_PM) == Calendar.PM ? "PM" : "AM"
        );
        calendar.add(Calendar.MINUTE,   halfAnhourIntervals ? 30 : 60);
        return interval;
    }

    public static void setHalfAnhourInterval(boolean halfAnhourInterval) {
        halfAnhourIntervals = halfAnhourInterval;
    }

}