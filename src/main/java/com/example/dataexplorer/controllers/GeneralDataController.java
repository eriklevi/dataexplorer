package com.example.dataexplorer.controllers;

import com.example.dataexplorer.entities.CountedPacket;
import com.example.dataexplorer.entities.MeanResult;
import com.example.dataexplorer.entities.PacketsStats;
import com.example.dataexplorer.services.GeneralDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/general")
public class GeneralDataController {

    private static final Logger logger = LoggerFactory.getLogger(GeneralDataController.class);

    @Autowired
    private GeneralDataService generalDataService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value= "/parsed", method = RequestMethod.GET)
    public PacketsStats getTotalParsed(){
        return this.generalDataService.getParsedPacketsStats();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value= "/parsed/{id}", method = RequestMethod.GET)
    public PacketsStats getTotalParsedBySnifferId(@PathVariable String id){
        return this.generalDataService.getParsedPacketsStatsBySnifferId(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value= "/counted/{id}/last", method = RequestMethod.GET)
    public CountedPacket getLastDeviceNumberEstimationById(@PathVariable String id){
        return this.generalDataService.getLastDeviceNumberEstimationById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value= "/counted/{id}/mean", method = RequestMethod.GET)
    public MeanResult getLastDeviceNumberEstimationById(@PathVariable String id, @RequestParam long timestamp){
        return this.generalDataService.getMeanestimation(id, timestamp);
    }

}
