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

import androidx.annotation.Nullable;

public class TimeSelectActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeselect);

        final Button pick = findViewById(R.id.button);
        pick.setText("Start Time");
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(TimeSelectActivity.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        pick.setText("Start Time:" + String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
                        Log.e("对话框", "hourOfDay = " + hourOfDay + ", minute = " + minute);
                    }
                }, 0, 0, true);
                timePicker.show();
            }
        });

        TextView textView = findViewById(R.id.eventText);
    }
}
