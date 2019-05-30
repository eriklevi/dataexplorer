package com.example.dataexplorer.services;

import com.example.dataexplorer.entities.DeviceData;
import com.example.dataexplorer.entities.DeviceInfo;

import java.util.List;

public interface DeviceTrackingService {
    List<DeviceData> getDeviceDataByDayAndMacAddress(long from, long to, String mac);
    List<DeviceInfo> getDistinctMacByDay(long from, long to);
}
