package com.survivor.calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

public class DatabaseHelper extends SQLiteOpenHelper {

    private DBManager dbManager;

    private SimpleCursorAdapter adapter;

    // Table Name
    public static final String TABLE_NAME = "Events";

    // Table columns
    public static final String _ID = "_id";
    public static final String Name = "name";
    public static final String Title = "title";
    public static final String Year = "year";
    public static final String Month = "month";
    public static final String Day = "day";
    public static final String Hour = "hour";
    public static final String Minute = "minute";
    public static final String Color = "color";
    public static final String Status = "status";


    // Database Information
    static final String DB_NAME = "Calendar.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Name + " TEXT NOT NULL, " + Title + " TEXT NOT NULL, " + Year + " TEXT NOT NULL,"
            + Month + " TEXT NOT NULL," + Day + " TEXT NOT NULL," + Hour + " TEXT NOT NULL," + Minute + " TEXT NOT NULL," +
            Color + " TEXT NOT NULL," + Status + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}