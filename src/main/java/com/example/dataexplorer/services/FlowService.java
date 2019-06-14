package com.example.dataexplorer.services;

import com.example.dataexplorer.entities.FlowData;
import com.example.dataexplorer.entities.PositionFlowData;

import java.util.List;

public interface FlowService {
    List<FlowData> getFlow(long from, long to);
    List<PositionFlowData> getFlow2(long from, long to);
}
