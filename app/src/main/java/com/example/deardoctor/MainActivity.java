package com.example.deardoctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.deardoctor.Weeks.AdapterClicked;
import com.example.deardoctor.Weeks.DisableTimes;
import com.example.deardoctor.Weeks.TimeControl;
import com.example.deardoctor.Weeks.TimeIntervalHelper;
import com.example.deardoctor.Weeks.WeeKAdapter;
import com.example.deardoctor.Weeks.WeekArray;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements  TimePickerDialog.OnTimeSetListener, AdapterClicked {
    @BindView(R.id.slotRecycler)
    RecyclerView slotsRecycler;
    ArrayList<WeekArray> weekArrays = new ArrayList<>();
    int total6 = 0, previous, above6, balance, prev;
    int[] num = new int[]{6, 5, 3, 1, 1, 1, 1, 1, 1, 9, 1,6, 5, 3, 1, 1, 1, 1, 1, 1, 9, 1};
    ArrayList<String> times = new ArrayList<>();
    ArrayList<Integer> myLine = new ArrayList<>();
    WeeKAdapter weeKAdapter;
    private static final Random random = new Random();
    private TimePickerDialog tpd;
    final ArrayList<String>  intervals = new ArrayList<>();
    int  positions = 0, clickedPosition, forTime;
    int startTime = 15;
    int endTime = 20 ;
    int randomColor() {
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
    final Random mRandom = new Random(System.currentTimeMillis());
    public int generateRandomColor() {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tpd = new TimePickerDialog();
        tpd.setOnTimeSetListener(this);
        calculate(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        ConstraintLayout.LayoutParams marginLayoutParams = new ConstraintLayout.LayoutParams(slotsRecycler.getLayoutParams());
        marginLayoutParams.setMargins(0, getStatusBarHeight(), 0, 0);
        slotsRecycler.setLayoutParams(marginLayoutParams);
    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clock, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clock){
            Calendar now = Calendar.getInstance();
            tpd = TimePickerDialog.newInstance(
                    MainActivity.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),false);
            tpd.setVersion(TimePickerDialog.Version.VERSION_2);
            tpd.show(getSupportFragmentManager(), "TimePicker");
            disable(tpd,20,0,15,0);
        }
        return super.onOptionsItemSelected(item);
    }
    void getTotats6(int number, String slotNum, int ranColor){
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
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        endTime = endTime +1;
        calculate(true);
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
                MainActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),false);
        tpd.setVersion(TimePickerDialog.Version.VERSION_2);
        tpd.show(getSupportFragmentManager(), "TimePicker");
        disable(tpd,20,0,15,0);
        tpd.setTimeInterval(1);
    }
    void calculate(boolean cal){
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
        Log.i("TAG","Clicked Position"+ intervals.size());
        if (cal) {
            Log.i("TAG","Clicked Position"+ clickedPosition+ "  "+startTime + " "+endTime +"  "+intervals.size());
            for (int i=clickedPosition-1;i<clickedPosition+15;i++){
                if (i%7!=0)
                    weekArrays.add(i,new WeekArray(1,"0",randomColor(),""));
            }
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this, 75);
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
                        return 15;
                }
            }
        });
        slotsRecycler.setLayoutManager(layoutManager);
        slotsRecycler.setAdapter(new WeeKAdapter(getApplicationContext(),weekArrays,intervals,myLine,this));
    }
}
