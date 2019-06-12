package com.example.dataexplorer.services;

import com.example.dataexplorer.entities.CountedPacket;
import com.example.dataexplorer.entities.MeanResult;
import com.example.dataexplorer.entities.PacketsStats;

public interface GeneralDataService {

    PacketsStats getParsedPacketsStats();
    PacketsStats getParsedPacketsStatsBySnifferId(String id);
    CountedPacket getLastDeviceNumberEstimationById(String id);
    MeanResult getMeanestimation(String id, long timestamp);
}
