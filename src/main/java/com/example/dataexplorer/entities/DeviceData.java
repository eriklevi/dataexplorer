package com.example.dataexplorer.entities;

public class DeviceData {
    private String mac;
    private long timestamp;
    private String snifferId;
    private String snifferName;
    private String snifferBuilding;
    private String snifferRoom;
    private int sequenceNumber;
    private float rssi;

    public DeviceData() {
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSnifferId() {
        return snifferId;
    }

    public void setSnifferId(String snifferId) {
        this.snifferId = snifferId;
    }

    public String getSnifferName() {
        return snifferName;
    }

    public void setSnifferName(String snifferName) {
        this.snifferName = snifferName;
    }

    public String getSnifferBuilding() {
        return snifferBuilding;
    }

    public void setSnifferBuilding(String snifferBuilding) {
        this.snifferBuilding = snifferBuilding;
    }

    public String getSnifferRoom() {
        return snifferRoom;
    }

    public void setSnifferRoom(String snifferRoom) {
        this.snifferRoom = snifferRoom;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public float getRssi() {
        return rssi;
    }

    public void setRssi(float rssi) {
        this.rssi = rssi;
    }
}
