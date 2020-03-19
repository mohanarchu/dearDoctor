package com.example.deardoctor.calender.utils;

import com.example.deardoctor.calender.model.CalendarEvent;

import java.util.Calendar;
import java.util.List;

/**
 * @author Mulham-Raee
 * @since v1.3.2
 */
public interface CalendarEventsPredicate {


    List<CalendarEvent> events(Calendar date);
}
