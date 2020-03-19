package com.example.deardoctor.slots;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deardoctor.MainActivity;
import com.example.deardoctor.R;
import com.example.deardoctor.Weeks.AdapterClicked;
import com.example.deardoctor.Weeks.DisableTimes;
import com.example.deardoctor.Weeks.TimeIntervalHelper;
import com.example.deardoctor.Weeks.WeeKAdapter;
import com.example.deardoctor.Weeks.WeekArray;
import com.example.deardoctor.base.FragmentBase;
import com.example.deardoctor.calender.HorizontalCalendar;
import com.example.deardoctor.calender.utils.HorizontalCalendarListener;
import com.example.deardoctor.constants.MySelectorDecorator;
import com.example.deardoctor.constants.OneDayDecorator;
import com.example.deardoctor.doctor.DoctorSelectPop;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Slots extends FragmentBase implements OnDateSelectedListener, TimePickerDialog.OnTimeSetListener, AdapterClicked {

    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;
    @BindView(R.id.slots_main_layout)
    LinearLayout slotsMainLayout;
    @BindView(R.id.slotRecycler)
    RecyclerView slotsRecycler;
    @BindView(R.id.chooseDoctors)
    FrameLayout chooseDoctors;
    @BindView(R.id.calanderMonth)
    TextView calanderMonth;

    @BindView(R.id.nextMonth)
    ImageView nextMonth;
    @BindView(R.id.previousMonth)
    ImageView previousMonth;
    private ArrayList<WeekArray> weekArrays = new ArrayList<>();
    private int total6 = 0, previous, above6, balance, prev;
    private int[] num = new int[]{6, 5, 3, 1, 1, 1, 1, 1, 1, 9, 1,6, 5, 3, 1, 1, 1, 1, 1, 1, 9, 1};
    private ArrayList<String> times = new ArrayList<>();
    private ArrayList<Integer> myLine = new ArrayList<>();
    WeeKAdapter weeKAdapter;
    private static final Random random = new Random();
    private TimePickerDialog tpd;
    private final ArrayList<String>  intervals = new ArrayList<>();
    private int  positions = 0, clickedPosition, forTime;
    private int startTime = 15;
    private int endTime = 20 ;
    Calendar calendar;
    private int randomColor() {
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
    private MySelectorDecorator mySelectorDecorator;
    CalendarDay selectedDate;
    private HorizontalCalendar horizontalCalendar;
    private final Random mRandom = new Random(System.currentTimeMillis());
    private int generateRandomColor() {
        final int baseColor = Color.WHITE;
        final int baseRed = Color.red(baseColor);
        final int baseGreen = Color.green(baseColor);
        final int baseBlue = Color.blue(baseColor);
        final int red = (baseRed + mRandom.nextInt(256)) / 2;
        final int green = (baseGreen + mRandom.nextInt(256)) / 2;
        final int blue = (baseBlue + mRandom.nextInt(256)) / 2;
        return Color.rgb(red, green, blue);
    }
    @Override
    protected void onViewBound(View view) {
        mySelectorDecorator = new MySelectorDecorator(Objects.requireNonNull(getActivity()),CalendarDay.today());
        calendarView.addDecorators(mySelectorDecorator, oneDayDecorator );
        tpd = new TimePickerDialog();
        tpd.setOnTimeSetListener(this);
        calendarView.setOnDateChangedListener(this);
        calculate(false);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH,0);
        /* end after 2 months from now */
        setDates(Calendar.getInstance());
        Calendar endDate = Calendar.getInstance();

        endDate.add(Calendar.MONTH, 4);
        final Calendar defaultSelectedDate = Calendar.getInstance();
        horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.myCalender)
                .range(startDate, endDate)
                .datesNumberOnScreen(7)
                .configure()
                .formatTopText("EEE")
                .formatMiddleText("dd")
                .formatBottomText("MMM")
                .textSize(12f, 14f, 14f)
                .showTopText(true)
                .showBottomText(false)
                .textColor(Color.WHITE, Color.WHITE).end()
                .build();
        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                Calendar calenda1 = Calendar.getInstance();
                calenda1.set(Calendar.MONTH,month+1);
                calenda1.set(Calendar.YEAR,year);
                calenda1.set(Calendar.DATE,1);
                horizontalCalendar.moveToNextMonth(calenda1,false);
            }
        });
        previousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                Calendar calenda1 = Calendar.getInstance();
                calenda1.set(Calendar.MONTH,month-1);
                calenda1.set(Calendar.DATE,1);
                calenda1.set(Calendar.YEAR,year);
                horizontalCalendar.moveToNextMonth(calenda1,false);
            }
        });

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                setDates(date);
                //Toast.makeText(getContext(), DateFormat.format("EEE, MMM d, yyyy", date) + " is selected!", Toast.LENGTH_SHORT).show();
            }
        });
        chooseDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               DoctorSelectPop dialogFragment = new DoctorSelectPop();
                Bundle bundle = new Bundle();
                bundle.putBoolean("notAlertDialog", true);
                dialogFragment.setArguments(bundle);
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                dialogFragment.show(ft, "dialog");
            }
        });
    }

    private void setDates(Calendar date){
        @SuppressLint("DefaultLocale") String interval = String.format(
                "%s %4d",
                String.format(Locale.US,"%tB",date),
                date.get(Calendar.YEAR)
        );
        calanderMonth.setText(interval);
        calendar = date;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_slots;
    }
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

     ///   oneDayDecorator.setDate(date.getDate());
        mySelectorDecorator.setDate(date.getDate());

//        mySelectorDecorator = new MySelectorDecorator(Objects.requireNonNull(getActivity()),CalendarDay.from(date.getDate()));
//        calendarView.addDecorators(mySelectorDecorator, oneDayDecorator );
        widget.invalidateDecorators();
    }
    private void getTotats6(int number, String slotNum, int ranColor){
        add(number);
        if (total6>6) {
            int numbers = total6 - 6;
            int total = 6 - previous;
            if (total != 0){
                times.add("");
                weekArrays.add(new WeekArray(total,slotNum,ranColor,""));
            }
            if (positions  != intervals.size()){
                times.add(intervals.get(positions));
                weekArrays.add(new WeekArray(-1,"",0,intervals.get(positions)));
                positions++;
            }
            showBalance(numbers,slotNum,ranColor) ;
        } else {
            if (number == 6){
                weekArrays.add(new WeekArray(-1,"",0,intervals.get(positions)));
                times.add(intervals.get(positions));
                positions++;
            }
            previous = total6;
            times.add("");
            weekArrays.add(new WeekArray(number,slotNum,ranColor,""));
        }
    }
    void showBalance(int bal,String slotNumber,int color){
        total6 = 0;
        previous = bal;
        add(bal);
        if (bal != 0){
            times.add("");
            weekArrays.add(new WeekArray(bal,slotNumber,color,""));
        }
    }
    void add(int value){
        total6+=value;
    }
    void disable(TimePickerDialog timePickerDialog,int fromTime,int fromMinute, int toTime,int toMinutes) {
        ArrayList<Timepoint> timepoints = new ArrayList<>();
        DisableTimes.disableTime(fromTime,fromMinute,toTime,toMinutes,timepoints);
        Timepoint [] stockArr = new Timepoint[timepoints.size()];
        for (int i=0;i<timepoints.size();i++){
            stockArr[i] = new Timepoint(timepoints.get(i).getHour(),timepoints.get(i).getMinute());
        }
        timePickerDialog.setDisabledTimes(stockArr);
    }
    @Override
    public void showPos(WeeKAdapter.timeView timeView, int pos, ArrayList<WeekArray> weekArray) {
        String time = weekArray.get(pos).getEnd().replace(".",":").replace(": PM","").trim();
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            Date dt = sdf.parse(time);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfs = new SimpleDateFormat("hh");
            String formatedTime = sdfs.format(Objects.requireNonNull(dt));
            forTime = Integer.valueOf(formatedTime.trim());
            int pre = intervals.indexOf(weekArray.get(pos).getEnd());
            clickedPosition = times.indexOf(intervals.get(pre+1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar now = Calendar.getInstance();
        tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),false);
        tpd.setVersion(TimePickerDialog.Version.VERSION_2);
        tpd.show(getChildFragmentManager(), "TimePicker");
        disable(tpd,20,0,15,0);
        tpd.setTimeInterval(1);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        endTime = endTime +1;
        calculate(true);
    }
    private void calculate(boolean cal){
        total6 = 0;
        previous = 0;
        times.clear();
        positions = 0;
        intervals.clear();
        weekArrays.clear();
        TimeIntervalHelper.setHalfAnhourInterval(true);
        TimeIntervalHelper.generateTimeInterval(intervals, startTime, Calendar.PM, Calendar.PM, false, endTime, false);
        for (int i=0;i<num.length;i++){
            if (num[i]<=6) {
                getTotats6(num[i],String.valueOf(i+1),generateRandomColor());
            } else {
                int color = generateRandomColor();
                int total = 6 - previous;
                if (total != 0) {
                    getTotats6(total,String.valueOf(i+1),color);
                }
                int value = num[i]-total;
                above6 = value/6;
                balance = value - (above6*6);
                for (int l=0;l<above6;l++) {
                    getTotats6(6,String.valueOf(i+1),color);
                }
                if (balance != 0){
                    getTotats6(balance,String.valueOf(i+1),color);
                }
            }
        }

        if (cal) {

            for (int i=clickedPosition-1;i<clickedPosition+15;i++){
                if (i%7!=0)
                    weekArrays.add(i,new WeekArray(1,"0",randomColor(),""));
            }
        }
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 74);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (weekArrays.get(position).getSlopCount()) {
                    case 1:
                        return 10;
                    case 2:
                        return 20;
                    case 3:
                        return 30;
                    case 4:
                        return 40;
                    case 5:
                        return 50;
                    case 6:
                        return 60;
                    default:
                        return 14;
                }
            }
        });
        slotsRecycler.setLayoutManager(layoutManager);
        slotsRecycler.setAdapter(new WeeKAdapter(getActivity(),weekArrays,intervals,myLine,this));
    }
}
