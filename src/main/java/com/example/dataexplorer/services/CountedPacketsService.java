package com.example.dataexplorer.services;

import com.example.dataexplorer.entities.CountedpacketsResult;

import java.util.List;

public interface CountedPacketsService {
    List<CountedpacketsResult> getResultsByBuilding(String building, long from, long to, String resolution);
    List<CountedpacketsResult> getResultsByRoom(String building, String room, long from, long to, String resolution);
    List<CountedpacketsResult> getResultsBySniffer(String building, String room, String sniffer, long from, long to, String resolution);
}
