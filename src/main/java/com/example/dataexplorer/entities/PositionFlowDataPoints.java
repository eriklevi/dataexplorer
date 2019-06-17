package com.example.dataexplorer.entities;

public class PositionFlowDataPoints {

    private long startTimestamp;
    private String snifferId;
    private int rssi;

    public PositionFlowDataPoints() {
    }



    public long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getSnifferId() {
        return snifferId;
    }

    public void setSnifferId(String snifferId) {
        this.snifferId = snifferId;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }
}
