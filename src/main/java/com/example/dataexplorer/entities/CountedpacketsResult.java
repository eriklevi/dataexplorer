package com.example.dataexplorer.entities;

public class CountedpacketsResult {
    private String snifferMac;
    private String snifferName;
    private String buildingName;
    private String roomName;
    private int totalPackets;
    private int globalPackets;
    private int localPackets;
    private int totalDistinctMacAddresses;
    private int totalDistinctFingerprints;
    private int avgEstimatedDevices;
    private long startTimestamp;
    private int year;
    private int month;
    private int weekOfYear;
    private int dayOfMonth;
    private int dayOfWeek;
    private int hour;
    private int fiveMinute;
    private int minute;

    public CountedpacketsResult() {
    }

    public String getSnifferMac() {
        return snifferMac;
    }

    public void setSnifferMac(String snifferMac) {
        this.snifferMac = snifferMac;
    }

    public String getSnifferName() {
        return snifferName;
    }

    public void setSnifferName(String snifferName) {
        this.snifferName = snifferName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getTotalPackets() {
        return totalPackets;
    }

    public void setTotalPackets(int totalPackets) {
        this.totalPackets = totalPackets;
    }

    public int getGlobalPackets() {
        return globalPackets;
    }

    public void setGlobalPackets(int globalPackets) {
        this.globalPackets = globalPackets;
    }

    public int getLocalPackets() {
        return localPackets;
    }

    public void setLocalPackets(int localPackets) {
        this.localPackets = localPackets;
    }

    public int getTotalDistinctMacAddresses() {
        return totalDistinctMacAddresses;
    }

    public void setTotalDistinctMacAddresses(int totalDistinctMacAddresses) {
        this.totalDistinctMacAddresses = totalDistinctMacAddresses;
    }

    public int getTotalDistinctFingerprints() {
        return totalDistinctFingerprints;
    }

    public void setTotalDistinctFingerprints(int totalDistinctFingerprints) {
        this.totalDistinctFingerprints = totalDistinctFingerprints;
    }

    public int getAvgEstimatedDevices() {
        return avgEstimatedDevices;
    }

    public void setAvgEstimatedDevices(int avgEstimatedDevices) {
        this.avgEstimatedDevices = avgEstimatedDevices;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
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

    public int getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(int weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
