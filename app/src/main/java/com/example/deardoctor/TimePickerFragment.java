package com.example.deardoctor;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.deardoctor.Weeks.DisableTimes;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;
import com.wdullaer.materialdatetimepicker.time.TimepointLimiter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private TextView timeTextView;
    private CheckBox mode24Hours;
    private CheckBox modeDarkTime;
    private CheckBox modeCustomAccentTime;
    private CheckBox vibrateTime;
    private CheckBox dismissTime;
    private CheckBox titleTime;
    private CheckBox enableSeconds;
    private CheckBox limitSelectableTimes;
    private CheckBox disableSpecificTimes;
    private CheckBox showVersion2;
    private TimePickerDialog tpd;

    public TimePickerFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timepicker_layout);
        tpd = new TimePickerDialog();
        tpd.setOnTimeSetListener(this);
        // Find our View instances
        timeTextView = findViewById(R.id.time_textview);
        Button timeButton = findViewById(R.id.time_button);
        Button original = findViewById(R.id.original_button);
        mode24Hours = findViewById(R.id.mode_24_hours);
        modeDarkTime = findViewById(R.id.mode_dark_time);
        modeCustomAccentTime = findViewById(R.id.mode_custom_accent_time);
        vibrateTime = findViewById(R.id.vibrate_time);
        dismissTime = findViewById(R.id.dismiss_time);
        titleTime = findViewById(R.id.title_time);
        enableSeconds = findViewById(R.id.enable_seconds);
        limitSelectableTimes = findViewById(R.id.limit_times);
        disableSpecificTimes = findViewById(R.id.disable_times);
        showVersion2 = findViewById(R.id.show_version_2);
         original.setOnClickListener(view1 -> {
            Calendar now = Calendar.getInstance();
            new android.app.TimePickerDialog(
                    this,
                    (view11, hour, minute) -> Log.d("Original", "Got clicked"),
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    mode24Hours.isChecked()
            ).show();
        });


        // Show a timepicker when the timeButton is clicked
        timeButton.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            /*
            It is recommended to always create a new instance whenever you need to show a Dialog.
            The sample app is reusing them because it is useful when looking for regressions
            during testing
             */
            if (tpd == null) {
                tpd = TimePickerDialog.newInstance(
                        TimePickerFragment.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        mode24Hours.isChecked()
                );
            } else {
                tpd.initialize(
                        TimePickerFragment.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        now.get(Calendar.SECOND),
                        mode24Hours.isChecked()
                );
            }
            tpd.setThemeDark(modeDarkTime.isChecked());
            tpd.vibrate(vibrateTime.isChecked());
            tpd.dismissOnPause(dismissTime.isChecked());
            tpd.enableSeconds(enableSeconds.isChecked());
            tpd.setVersion(showVersion2.isChecked() ? TimePickerDialog.Version.VERSION_2 : TimePickerDialog.Version.VERSION_1);
            if (modeCustomAccentTime.isChecked()) {
                tpd.setAccentColor(Color.parseColor("#9C27B0"));
            }
            if (titleTime.isChecked()) {
                tpd.setTitle("TimePicker Title");
            }
            if (limitSelectableTimes.isChecked()) {
                if (enableSeconds.isChecked()) {
                    tpd.setTimeInterval(3, 5, 10);
                } else {
                    tpd.setTimeInterval(3, 5, 60);
                }
            }
            ArrayList<Timepoint> timepoints = new ArrayList<>();
            DisableTimes.disableTime(3,30,4,30,timepoints);
            Timepoint [] stockArr = new Timepoint[timepoints.size()];

            for (int i=0;i<timepoints.size();i++){
                stockArr[i] = new Timepoint(timepoints.get(i).getHour(),timepoints.get(i).getMinute());
                Log.i("TAG", "My TImess" + timepoints.get(i).getHour() +"   "+timepoints.get(i).getMinute());
            }

            if (disableSpecificTimes.isChecked()) {
                Timepoint[] disabledTimes = {
                        new Timepoint(10),
                        new Timepoint(10, 30),
                        new Timepoint(11),
                        new Timepoint(12, 30)
                };
              tpd.setDisabledTimes(stockArr);
            }
            tpd.setOnCancelListener(dialogInterface -> {
                Log.d("TimePicker", "Dialog was cancelled");
                tpd = null;
            });
            tpd.show(getSupportFragmentManager(), "Timepickerdialog");
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tpd = null;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String secondString = second < 10 ? "0"+second : ""+second;
        String time = "You picked the following time: "+hourString+"h"+minuteString+"m"+secondString+"s";
        timeTextView.setText(time);
        tpd = null;
    }
}