package com.survivor.calendar;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(Event event) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.Name, event.getName());
        contentValue.put(DatabaseHelper.Title, event.getTitle());
        contentValue.put(DatabaseHelper.Year, event.getYear());
        contentValue.put(DatabaseHelper.Month, event.getMonth());
        contentValue.put(DatabaseHelper.Day, event.getDay());
        contentValue.put(DatabaseHelper.Hour, event.getHour());
        contentValue.put(DatabaseHelper.Minute, event.getMinute());
        contentValue.put(DatabaseHelper.Color, event.getColor());
        contentValue.put(DatabaseHelper.Status, event.getStatus());
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

//    public void delete(String id) {
//        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + " = ?", new String[]{id});
//    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.Name, DatabaseHelper.Title, DatabaseHelper.Year,
                DatabaseHelper.Month, DatabaseHelper.Day, DatabaseHelper.Hour, DatabaseHelper.Minute, DatabaseHelper.Color, DatabaseHelper.Status};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor findByDay(String year, String month, String day){
        String[] columns= new String[] { DatabaseHelper._ID, DatabaseHelper.Name, DatabaseHelper.Title, DatabaseHelper.Year,
                DatabaseHelper.Month, DatabaseHelper.Day, DatabaseHelper.Hour, DatabaseHelper.Minute, DatabaseHelper.Color, DatabaseHelper.Status};
        String condition = DatabaseHelper.Year + "=? and " + DatabaseHelper.Month + "=? and " + DatabaseHelper.Day + "=?";
        String[] selectionArgs={year, month, day};//具体的条件,注意要对应条件字段
        Cursor cursor=database.query(DatabaseHelper.TABLE_NAME, columns, condition, selectionArgs, null, null, null, null);
        return cursor;
    }

    public int update(long _id, Event event) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.Name, event.getName());
        contentValues.put(DatabaseHelper.Title, event.getTitle());
        contentValues.put(DatabaseHelper.Year, event.getYear());
        contentValues.put(DatabaseHelper.Month, event.getMonth());
        contentValues.put(DatabaseHelper.Day, event.getDay());
        contentValues.put(DatabaseHelper.Hour, event.getHour());
        contentValues.put(DatabaseHelper.Minute, event.getMinute());
        contentValues.put(DatabaseHelper.Color, event.getColor());
        contentValues.put(DatabaseHelper.Status, event.getStatus());
        return database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}