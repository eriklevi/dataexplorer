package com.example.dataexplorer.services;

import com.example.dataexplorer.entities.FlowData;

import java.util.List;

public interface FlowService {
    List<FlowData> getFlow(long from, long to);
}
