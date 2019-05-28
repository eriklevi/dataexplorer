package com.example.dataexplorer.services;

import com.example.dataexplorer.entities.DeviceData;

import java.util.List;

public interface DeviceTrackingService {
    List<DeviceData> getDeviceDataByDayAndMacAddress(int year, int month, int day, String mac);
}
