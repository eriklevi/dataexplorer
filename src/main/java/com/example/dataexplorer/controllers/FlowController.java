package com.example.dataexplorer.controllers;

import com.example.dataexplorer.entities.CountedpacketsResult;
import com.example.dataexplorer.entities.FlowData;
import com.example.dataexplorer.services.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FlowController {

    @Autowired
    private FlowService flowService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value= "/flow", method = RequestMethod.GET)
    public List<FlowData> getFlow(@RequestParam long from, @RequestParam long to){
        return flowService.getFlow(from, to);
    }
}
