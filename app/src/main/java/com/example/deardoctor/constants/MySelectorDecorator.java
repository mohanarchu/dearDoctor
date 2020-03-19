package com.example.deardoctor.constants;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.example.deardoctor.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.threeten.bp.LocalDate;

public class MySelectorDecorator implements DayViewDecorator {

  private final Drawable drawable;
  CalendarDay calendarDay;

  public MySelectorDecorator(Activity context,CalendarDay day) {
    drawable = context.getResources().getDrawable(R.drawable.my_selector);
    calendarDay = day;
    Log.i("TAG","Calender day"+ day);
  }
  @Override
  public boolean shouldDecorate(CalendarDay day) {
    return day.equals(calendarDay);
  }
  @Override
  public void decorate(DayViewFacade view) {
    view.addSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)));
   view.setSelectionDrawable(drawable);
  }
  public void setDate(LocalDate date) {
    this.calendarDay = CalendarDay.from(date);
  }

}
