package com.survivor.calendar;

import java.sql.Timestamp;

public class Event {
    public Event(String name, String title, int year, int month, int day, int hour, int minute, String color, String status) {
        this.name = name;
        this.title = title;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.color = color;
        this.status = status;
    }

    private String name;
    private String title;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String color;
    private String status;

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getColor() {
        return color;
    }

    public String getStatus() {
        return status;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                ", color=" + color +
                ", status='" + status + '\'' +
                '}';
    }



}
