package com.example.dataexplorer.controllers;

import com.example.dataexplorer.entities.CountedpacketsResult;
import com.example.dataexplorer.entities.DeviceData;
import com.example.dataexplorer.services.DeviceTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeviceTrackingController {

    @Autowired
    DeviceTrackingService deviceTrackingService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value= "/track", method = RequestMethod.GET)
    public List<DeviceData> getResultsByBuilding(@RequestParam int year
            , @RequestParam int month
            , @RequestParam int day
            , @RequestParam String mac){
        return deviceTrackingService.getDeviceDataByDayAndMacAddress(year, month, day, mac);
    }
}
