package com.example.dataexplorer.controllers;

import com.example.dataexplorer.entities.DeviceData;
import com.example.dataexplorer.entities.DeviceInfo;
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
    public List<DeviceData> getResultsByBuilding(@RequestParam long from
            , @RequestParam long to
            , @RequestParam String mac){
        return deviceTrackingService.getDeviceDataByDayAndMacAddress(from, to, mac);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value= "/track/deviceinfo", method = RequestMethod.GET)
    public List<DeviceInfo> getResultsByBuilding(@RequestParam long from
            , @RequestParam long to){
        return deviceTrackingService.getDistinctMacByDay(from, to);
    }


}
