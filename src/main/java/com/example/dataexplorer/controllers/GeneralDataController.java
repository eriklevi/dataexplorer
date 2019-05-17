package com.example.dataexplorer.controllers;

import com.example.dataexplorer.services.GeneralDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/general")
public class GeneralDataController {

    private static final Logger logger = LoggerFactory.getLogger(GeneralDataController.class);

    @Autowired
    private GeneralDataService generalDataService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value= "/parsed", method = RequestMethod.GET)
    public long getResultsParsedBySnifferMac(@RequestParam(name = "mac", required = false) String snifferMac
            , @RequestParam(name = "building", required = false) String building
            , @RequestParam(name = "room", required = false) String room){
        if(building == null && room == null)
            return this.generalDataService.getTotalParsedPacketsBySnifferMac(snifferMac);
        else return 0;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value= "/raw", method = RequestMethod.GET)
    public long getResultsRawBySnifferMacAndLocation(@RequestParam(name = "mac", required = false) String snifferMac
            , @RequestParam(name = "building", required = false) String building
            , @RequestParam(name = "room", required = false) String room){
        return this.generalDataService.getTotalRawPacketsBySnifferMac(snifferMac);
    }



}
