package com.example.dataexplorer.services;

import com.example.dataexplorer.entities.FlowData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class FlowServiceImpl implements FlowService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<FlowData> getFlow(long from, long to) {
        MatchOperation matchOperation = match(new Criteria("timestamp").gte(from).lt(to));
        ProjectionOperation projectionOperation = project("rssi","snifferId", "year", "month", "dayOfMonth", "hour", "fiveMinute")
                .andExpression("(rssi + 100) / 100").as("normalizedRssi");
        GroupOperation groupOperation = group("snifferId", "year", "month", "dayOfMonth", "hour", "fiveMinute")
                .sum("normalizedRssi").as("heat");
        SortOperation sortOperation = sort(new Sort(Sort.Direction.ASC, "year", "month", "dayOfMonth", "hour", "fiveMinute"));
        Aggregation aggregation = newAggregation(matchOperation, projectionOperation,groupOperation, sortOperation);
        AggregationResults aggregationResults = mongoTemplate.aggregate(aggregation, "parsedPackets", FlowData.class);
        return aggregationResults.getMappedResults();
    }
}
