package com.example.dataexplorer.entities;

public class DeviceData {
    String snifferMac;
    String timestamp;
    String snifferId;
    String snifferName;
    String snifferBuilding;
    String snifferRoom;
    int sequenceNumber;

    public DeviceData() {
    }

    public String getSnifferMac() {
        return snifferMac;
    }

    public void setSnifferMac(String snifferMac) {
        this.snifferMac = snifferMac;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
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
