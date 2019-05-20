package com.example.dataexplorer.services;

import com.example.dataexplorer.entities.PacketsStats;

import java.util.List;

public interface GeneralDataService {

    PacketsStats getParsedPacketsStats();
    PacketsStats getParsedPacketsStatsBySnifferId(String id);
}
