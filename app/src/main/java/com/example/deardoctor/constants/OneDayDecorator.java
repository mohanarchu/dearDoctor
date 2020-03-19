package com.example.deardoctor.constants;


import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import org.threeten.bp.LocalDate;

public class OneDayDecorator implements DayViewDecorator {

  private CalendarDay date;


  public OneDayDecorator() {
    date = CalendarDay.today();
  }

  @Override
  public boolean shouldDecorate(CalendarDay day) {
    return day.equals(date);
  }

  @Override
  public void decorate(DayViewFacade view) {

    view.addSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)));
    view.addSpan(new StyleSpan(Typeface.BOLD));
    view.addSpan(new RelativeSizeSpan(1.5f));
  }


  public void setDate(LocalDate date) {
    this.date = CalendarDay.from(date);
  }
}
