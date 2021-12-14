package com.survivor.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import studio.carbonylgroup.textfieldboxes.SimpleTextChangedWatcher;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class UserActivity extends Activity {

    String user = "default";
    String color = Integer.toHexString(Color.WHITE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        TopView tv = findViewById(R.id.top_view_user);
        tv.setTitle("User");
        tv.setRightTitle("");
        tv.setOnclickLeft(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final TextFieldBoxes textFieldBoxes = findViewById(R.id.text_field_boxes_user);
        textFieldBoxes.setSimpleTextChangeWatcher(new SimpleTextChangedWatcher() {
            @Override
            public void onTextChanged(String theNewText, boolean isError) {
                user = theNewText;
            }
        });

        Button color_button = findViewById(R.id.color_set);
        color_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor();
            }
        });

        Button finish = findViewById(R.id.button_finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user == null || user.equals("") || user.equals("default")){
                    Toast.makeText(getApplicationContext(), "Please Enter The User Name", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = getIntent();
                    intent.putExtra("color", color + "");
                    intent.putExtra("user", user);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    private void setColor(){
        ColorPickerDialogBuilder
                .with(UserActivity.this)
                .setTitle("Choose Color")
                .initialColor(Color.WHITE)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
//                        toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
//                        Log.e("a", Integer.toHexString(selectedColor) + "");
                        color = Integer.toHexString(selectedColor);
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
//                        changeBackgroundColor(selectedColor);
//                        Log.e("a", "a");
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .noSliders()
                .build()
                .show();
    }
}
