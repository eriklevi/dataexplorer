package com.example.dataexplorer.entities;


import java.util.List;

public class FlowData {
    private List<String> snifferId;
    private int year;
    private int month;
    private int dayOfMonth;
    private int hour;
    private int fiveMinute;
    private List<Integer> heat;

    public FlowData() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getFiveMinute() {
        return fiveMinute;
    }

    public void setFiveMinute(int fiveMinute) {
        this.fiveMinute = fiveMinute;
    }

    public List<Integer> getHeat() {
        return heat;
    }

    public void setHeat(List<Integer> heat) {
        this.heat = heat;
    }

    public List<String> getSnifferId() {
        return snifferId;
    }

    public void setSnifferId(List<String> snifferId) {
        this.snifferId = snifferId;
    }
}
