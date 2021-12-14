package com.survivor.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import studio.carbonylgroup.textfieldboxes.SimpleTextChangedWatcher;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class TimeSelectActivity extends Activity {

    private DBManager dbManager;

    private String eventText = "";

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String color;
    private String status = "private";
    private String user = "default";

    Date date = new Date();
    SimpleDateFormat dateFormat1= new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dateFormat2= new SimpleDateFormat("hh:mm");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        user = intent.getExtras().getString("user");
        color = intent.getExtras().getString("color");

        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        day = now.get(Calendar.DAY_OF_MONTH);
        hour = now.get(Calendar.HOUR_OF_DAY);
        minute = now.get(Calendar.MINUTE);

        setContentView(R.layout.activity_timeselect);

        setTitle();
        setButton();
        setText();


    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    private void setText(){
        final TextFieldBoxes textFieldBoxes = findViewById(R.id.text_field_boxes);
        textFieldBoxes.setSimpleTextChangeWatcher(new SimpleTextChangedWatcher() {
            @Override
            public void onTextChanged(String theNewText, boolean isError) {
                eventText = theNewText;
            }
        });
    }

    private void setTitle() {
        // set title, left button, right button
        TopView tv = findViewById(R.id.top_view);
        tv.setTitle("Add Event");
        tv.setOnclickLeft(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv.setRightTitle("");
    }

    private void setButton() {
        final Button pick = findViewById(R.id.button);
        pick.setText(dateFormat1.format(date));
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerView pvTime = new TimePickerBuilder(TimeSelectActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        Toast.makeText(TimeSelectActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                        pick.setText(dateFormat1.format(date));

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        year = cal.get(Calendar.YEAR);
                        month = cal.get(Calendar.MONTH) + 1;
                        day = cal.get(Calendar.DAY_OF_MONTH);
                    }
                })
                        .setLabel("年","月","日","时","分","秒")
                        .build();
                pvTime.show();

            }
        });

        final Button pick2 = findViewById(R.id.button2);
        pick2.setText(dateFormat2.format(date));
        pick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerView pvTime = new TimePickerBuilder(TimeSelectActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        Toast.makeText(TimeSelectActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                        pick2.setText(dateFormat2.format(date));

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        hour = cal.get(Calendar.HOUR_OF_DAY);
                        minute = cal.get(Calendar.MINUTE);
                    }
                })
                        .setLabel("年","月","日","时","分","秒")
                        .setType(new boolean[]{false, false, false, true, true, false})
                        .build();
                pvTime.show();

            }
        });

        //confirm button
        Button confirmButton = findViewById(R.id.ConfirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eventText.equals("")){
                    Toast.makeText(TimeSelectActivity.this, "Enter Your Time Please", Toast.LENGTH_SHORT).show();
                }else {
                    dbManager = new DBManager(TimeSelectActivity.this);
                    dbManager.open();
                    dbManager.insert(new Event(user, eventText, year, month, day, hour, minute, color, status));
                    dbManager.close();
                    finish();
                }
            }
        });

        //switch button
        Switch sb = findViewById(R.id.switch1);
        sb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    status = "public";
                }else {
                    status = "private";
                }
            }
        });
    }
}
