package com.survivor.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeSelectActivity extends Activity {

    Date date = new Date();
    SimpleDateFormat dateFormat1= new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dateFormat2= new SimpleDateFormat("hh:mm");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeselect);

        final Button pick = findViewById(R.id.button);
        pick.setText("Date:" + dateFormat1.format(date));
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TimePickerDialog timePicker = new TimePickerDialog(TimeSelectActivity.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        pick.setText("Start Time:" + String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
//                        Log.e("对话框", "hourOfDay = " + hourOfDay + ", minute = " + minute);
//                    }
//                }, 0, 0, true);
//                timePicker.show();
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(TimeSelectActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        Toast.makeText(TimeSelectActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                        pick.setText("Date:" + dateFormat1.format(date));
                    }
                })
                        .setLabel("年","月","日","时","分","秒")
                        .build();
                pvTime.show();

            }
        });

        final Button pick2 = findViewById(R.id.button2);
        pick2.setText("Start Time:" + dateFormat2.format(date));
        pick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    TimePickerView pvTime = new TimePickerBuilder(TimeSelectActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        Toast.makeText(TimeSelectActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                        pick2.setText("Start Time:" + dateFormat2.format(date));
                    }
                })
                            .setLabel("年","月","日","时","分","秒")
                            .setType(new boolean[]{false, false, false, true, true, false})
                            .build();
                pvTime.show();

            }
        });

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
