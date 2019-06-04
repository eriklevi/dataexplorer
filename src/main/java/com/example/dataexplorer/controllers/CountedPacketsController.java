package com.example.dataexplorer.controllers;

import com.example.dataexplorer.entities.CountedpacketsResult;
import com.example.dataexplorer.services.CountedPacketsService;
import com.example.dataexplorer.services.GeneralDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/counted")
public class CountedPacketsController {

    private static final Logger logger = LoggerFactory.getLogger(CountedPacketsController.class);

    @Autowired
    CountedPacketsService countedPacketsService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value= "/{building}", method = RequestMethod.GET)
    public List<CountedpacketsResult> getResultsByBuilding(@PathVariable String building
            , @RequestParam long from
            , @RequestParam long to
            , @RequestParam String mode
            , @RequestParam String resolution){
        return countedPacketsService.getResultsByBuilding(building, from, to, resolution);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value= "/{building}/{room}", method = RequestMethod.GET)
    public List<CountedpacketsResult> getResultsByRoom(@PathVariable String building
            , @PathVariable String room
            , @RequestParam long from
            , @RequestParam long to
            , @RequestParam String mode
            , @RequestParam String resolution){
        return countedPacketsService.getResultsByRoom(building, room, from, to, resolution);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value= "/{building}/{room}/{sniffer}", method = RequestMethod.GET)
    public List<CountedpacketsResult> getResultsBySniffer(@PathVariable String building
            , @PathVariable String room
            , @PathVariable String sniffer
            , @RequestParam long from
            , @RequestParam long to
            , @RequestParam String mode
            , @RequestParam String resolution){
        return countedPacketsService.getResultsBySniffer(building, room, sniffer, from, to, resolution);
    }
}
