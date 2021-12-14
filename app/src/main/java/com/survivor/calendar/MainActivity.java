package com.survivor.calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.survivor.calendarview.Calendar;
import com.survivor.calendarview.CalendarView;
import com.survivor.calendar.R;
import com.survivor.calendar.base.activity.BaseActivity;
import com.survivor.calendar.colorful.ColorfulActivity;
import com.survivor.calendar.custom.CustomActivity;
import com.survivor.calendar.index.IndexActivity;
import com.survivor.calendar.simple.SimpleActivity;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.survivor.calendar.base.activity.BaseActivity;

public class MainActivity extends BaseActivity implements CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener,
        View.OnClickListener {

    String user = "default";
    String color = Integer.toHexString(Color.WHITE);

    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    private int mYear;

    private TextView getTime;
    private Calendar calendar;// 用来装日期的
    private DatePickerDialog dialog;
    private DBManager dbManager;

    CalendarView mCalendarView;

    public static void show(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_full;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {

        setStatusBarDarkMode();
        mTextMonthDay = findViewById(R.id.tv_month_day);
        mTextYear = findViewById(R.id.tv_year);
        mTextLunar = findViewById(R.id.tv_lunar);
        mTextCurrentDay = findViewById(R.id.tv_current_day);
        mCalendarView = findViewById(R.id.calendarView);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));

//                FloatingActionButton fb = findViewById(R.id.floatingActionButton1);
//                fb.setVisibility(View.GONE);
//                FloatingActionButton fb2 = findViewById(R.id.floatingActionButton2);
//                fb2.setVisibility(View.GONE);
//                FloatingActionButton fb3 = findViewById(R.id.floatingActionButton3);
//                fb3.setVisibility(View.GONE);
            }
        });
        findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });

        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "." + mCalendarView.getCurDay());
        mTextLunar.setText("User: " + user);
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));



        FloatingActionButton fab = findViewById(R.id.floatingActionButton1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TimeSelectActivity.class);
                intent.putExtra("color", color);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        FloatingActionButton fab2 = findViewById(R.id.floatingActionButton2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("user", user);

//                String[] month_day = String.valueOf(mTextMonthDay.getText()).split("月");
//                String[] day = String.valueOf(month_day[1]).split("日");
//                intent.putExtra("year", mTextYear.getText());
//                intent.putExtra("month",  month_day[0]);
//                intent.putExtra("day", day[0]);
                String[] month_day = String.valueOf(mTextMonthDay.getText()).split("\\.");
                intent.putExtra("year", mTextYear.getText());
                intent.putExtra("month",  month_day[0]);
                intent.putExtra("day", month_day[1]);
                startActivity(intent);
            }
        });
        FloatingActionButton fab3 = findViewById(R.id.floatingActionButton3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);

                startActivityForResult(intent,1);
//                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // SearchAddressInfo info = (SearchAddressInfo) data.getParcelableExtra("position");
            String selected_color = data.getStringExtra("color");
            String selected_user = data.getStringExtra("user");
            Log.e("color", selected_color);
            Log.e("user", selected_user);
            color = selected_color;
            user = selected_user;
            initView();
//            mTvClockInAddress.setText(position);
        }
    }

    @Override
    protected void initData() {
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();


//        Map<String, Calendar> map = new HashMap<>();
//        dbManager = new DBManager(MainActivity.this);
//        dbManager.open();
//        Cursor cursor = dbManager.fetch();
//        while (cursor.moveToNext()){
//            cursor.getString(2);
//            String name = cursor.getString(1);
//            String event_year = cursor.getString(3);
//            String event_month = cursor.getString(4);
//            String event_day = cursor.getString(5);
//            String event_color = cursor.getString(8);
//            int value = new BigInteger(event_color, 16).intValue();
//            Log.e("color", value + "");
//            map.put(getSchemeCalendar(Integer.parseInt(event_year), Integer.parseInt(event_month), Integer.parseInt(event_day), value, name).toString(),
//                    getSchemeCalendar(Integer.parseInt(event_year), Integer.parseInt(event_month), Integer.parseInt(event_day), value, name)); //0xFF13acf0
//        }
        Map<String, Calendar> map = new HashMap<>();
        dbManager = new DBManager(MainActivity.this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();
        while (cursor.moveToNext()){
            cursor.getString(2);
            String name = cursor.getString(1);
            String event_year = cursor.getString(3);
            String event_month = cursor.getString(4);
            String event_day = cursor.getString(5);
            Log.e("day", event_day);
            Log.e("day", String.valueOf(Integer.parseInt(event_day)));
            String event_color = cursor.getString(8);
            int value = new BigInteger(event_color, 16).intValue();
            if(map.containsKey(event_year + event_month + event_day)) {
                map.get(event_year + event_month + event_day).addScheme(value, "ok");
            }else {
                map.put(event_year + "" + (Integer.parseInt(event_month) < 10 ? "0" + event_month : event_month) + "" + (Integer.parseInt(event_day) < 10 ? "0" + event_day : event_day),
                        getSchemeCalendar(Integer.parseInt(event_year), Integer.parseInt(event_month), Integer.parseInt(event_day), value, name)); //0xFF13acf0
//                Log.e("date", event_year + event_month + event_day);
            }
        }

        mCalendarView.setSchemeDate(map);
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(color, "ok");
//        calendar.addScheme(day%2 == 0 ? 0xFF00CD00 : 0xFFD15FEE, "节");
//        calendar.addScheme(day%2 == 0 ? 0xFF660000 : 0xFF4169E1, "记");
//        calendar.addScheme(day%2 == 0 ? 0xFF660000 : 0xFF4169E1, "sad");
//        calendar.addScheme(day%2 == 0 ? 0xFF660000 : 0xFF4169E1, "qwe");
        return calendar;
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.ll_flyme:
//                CustomActivity.show(this);
//                break;
//            case R.id.ll_simple:
//                SimpleActivity.show(this);
//                break;
//            case R.id.ll_colorful:
//                ColorfulActivity.show(this);
//                break;
//            case R.id.ll_index:
//                IndexActivity.show(this);
//                break;
//        }
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "." + calendar.getDay());
        mTextYear.setText(String.valueOf(calendar.getYear()));
//        mTextLunar.setText(calendar.getLunar());
        mTextLunar.setText("User: " + user);
        mYear = calendar.getYear();

        Log.e("onDateSelected", "  -- " + calendar.getYear() +
                "  --  " + calendar.getMonth() +
                "  -- " + calendar.getDay() +
                "  --  " + isClick + "  --   " + calendar.getScheme());
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @Override
    public void onResume(){
        super.onResume();
        initData();
    }

//    private static Color fromStrToARGB(String str) {
//        String str1 = str.substring(0, 2);
//
//        String str2 = str.substring(2, 4);
//
//        String str3 = str.substring(4, 6);
//
//        String str4 = str.substring(6, 8);
//
//        int alpha = Integer.parseInt(str1, 16);
//
//        int red = Integer.parseInt(str2, 16);
//
//        int green = Integer.parseInt(str3, 16);
//
//        int blue = Integer.parseInt(str4, 16);
//
//        Color color = new Color(red, green, blue, alpha);
//
//        return color;
//
//    }
}