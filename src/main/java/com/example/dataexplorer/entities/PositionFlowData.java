package com.example.dataexplorer.entities;

import java.util.List;

public class PositionFlowData {
    private String fcs;
    private int hour;
    private int minute;
    private List<PositionFlowDataPoints> data;

    public PositionFlowData() {
    }

    public String getFcs() {
        return fcs;
    }

    public void setFcs(String fcs) {
        this.fcs = fcs;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public List<PositionFlowDataPoints> getData() {
        return data;
    }

    public void setData(List<PositionFlowDataPoints> data) {
        this.data = data;
    }
}
