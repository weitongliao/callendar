package com.survivor.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.ColorInt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends Activity {
    private ListView lv;
    private DBManager dbManager;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<Event> events = new ArrayList<>();

    String this_year;
    String this_month;
    String this_day;
    String user = "default";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setTitle();

        Intent intent = getIntent();
        this_year = intent.getExtras().getString("year");
        this_month = intent.getExtras().getString("month");
        this_day = intent.getExtras().getString("day");
        user = intent.getExtras().getString("user");

        setLv();
    }

    private void setLv(){
        list = new ArrayList<>();
        lv = (ListView) findViewById(R.id.lv);
        /*定义一个动态数组*/
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,     Object>>();
        /*在数组中存放数据*/
        dbManager = new DBManager(ListActivity.this);
        dbManager.open();
//        Cursor cursor = dbManager.fetch();
        Cursor cursor = dbManager.findByDay(this_year, this_month, this_day);
        while (cursor.moveToNext()){
            cursor.getString(2);
            HashMap<String, Object> map = new HashMap<String, Object>();
            list.add(cursor.getString(0));
            String name = cursor.getString(1);
            String title = cursor.getString(2);
            String year = cursor.getString(3);
            String month = cursor.getString(4);
            String day = cursor.getString(5);
            String hour = cursor.getString(6);
            String minute = cursor.getString(7);
            String color = cursor.getString(8);
            String status = cursor.getString(9);

//            int value = new BigInteger(color, 16).intValue();

            events.add(new Event(name, title, Integer.parseInt(year), Integer.parseInt(month),
                    Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute), color, status));
            if (status.equals("private") && !user.equals(name)){
                title = "[Private Issue]";
            }
            map.put("ItemTitle", name + ": " + title + color);
            map.put("ItemText", year + "-" + month + "-" + day + " " + hour + ":" + minute);
            listItem.add(map);
        }
        dbManager.close();
        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this,listItem,//需要绑定的数据
                R.layout.item,//每一行的布局
                //动态数组中的数据源的键对应到定义布局的View中
                new String[] {"ItemImage","ItemTitle", "ItemText"},
                new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemText});

        lv.setAdapter(mSimpleAdapter);//为ListView绑定适配器

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                setTitle("你点击了第"+arg2+"行");//设置标题栏显示点击的行
//                选中指定条目后显示弹窗
                DiyDialog1(arg2);
            }
        });
    }

    /**
     * 自定义1 控制普通的dialog的位置，大小，透明度
     * 在普通的dialog.show下面添加东西
     */
    private void DiyDialog1(int index){
        AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(ListActivity.this);
//        alterDiaglog.setIcon(R.drawable.a);//图标
        Event current = events.get(index);
        alterDiaglog.setTitle(current.getTitle());//文字
        String date = current.getYear() + "-" + current.getMonth() + "-" + current.getDay() + " " + current.getDay() + ":" + current.getMinute();
        alterDiaglog.setMessage(date);//提示消息
        //积极的选择
        alterDiaglog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        //消极的选择
        alterDiaglog.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbManager = new DBManager(ListActivity.this);
                dbManager.open();
                dbManager.delete(Long.parseLong(list.get(index)));
                setLv();
            }
        });



//        alterDiaglog.setNeutralButton("不生不死", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(ListActivity.this,"点击了不生不死",Toast.LENGTH_SHORT).show();
//            }
//        });
        AlertDialog dialog = alterDiaglog.create();

        //显示
        dialog.show();

        Button positiveButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.BLUE);
        Button negativeButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.RED);

        //自定义的东西
        //放在show()之后，不然有些属性是没有效果的，比如height和width
        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // 设置高度和宽度
        p.height = (int) (d.getHeight() * 0.25); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65

        p.gravity = Gravity.CENTER;//设置位置

        p.alpha = 0.8f;//设置透明度
        dialogWindow.setAttributes(p);
    }

    private void setTitle() {
        // set title, left button, right button
        TopView tv = findViewById(R.id.top_view_list);
        tv.setTitle("Event List");
        tv.setOnclickLeft(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv.setRightTitle("");
    }
}
