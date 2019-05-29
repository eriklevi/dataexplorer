package com.example.dataexplorer.services;

import com.example.dataexplorer.entities.DeviceData;
import com.example.dataexplorer.entities.DeviceInfo;

import java.util.List;

public interface DeviceTrackingService {
    List<DeviceData> getDeviceDataByDayAndMacAddress(int year, int month, int day, String mac);
    List<DeviceInfo> getDistinctMacByDay(int year, int month, int day, int hour);
}
