package com.example.dataexplorer.entities;

public class DeviceData {
    String mac;
    long timestamp;
    String snifferId;
    String snifferName;
    String snifferBuilding;
    String snifferRoom;
    int sequenceNumber;

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
}
