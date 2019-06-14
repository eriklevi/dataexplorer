package com.example.dataexplorer.services;

import com.example.dataexplorer.entities.FlowData;
import com.example.dataexplorer.entities.PositionFlowData;
import com.mongodb.BasicDBObject;
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
        ProjectionOperation projectionOperation = project("rssi","snifferId", "year", "month", "dayOfMonth", "hour", "fiveMinute", "deviceMac", "fingerprint")
                .andExpression("(rssi + 100) / 100").as("normalizedRssi");
        GroupOperation groupOperation = group("snifferId", "year", "month", "dayOfMonth", "hour", "fiveMinute")
                .sum("normalizedRssi").as("heat")
                .addToSet("deviceMac").as("macs")
                .addToSet("fingerprint").as("fingerprints");
        ProjectionOperation projectionOperation2 = project("heat","snifferId", "year", "month", "dayOfMonth", "hour", "fiveMinute")
                .andExpression("macs").size().as("distinctMacs")
                .andExpression("fingerprints").size().as("distinctFingerprints");
        GroupOperation groupOperation1 = group("year", "month", "dayOfMonth", "hour", "fiveMinute")
                .push("snifferId").as("snifferId")
                .push("heat").as("heat")
                .push("distinctMacs").as("distinctMacs")
                .push("distinctFingerprints").as("distinctFingerprints");
        SortOperation sortOperation = sort(new Sort(Sort.Direction.ASC, "year", "month", "dayOfMonth", "hour", "fiveMinute"));
        //Aggregation aggregation = newAggregation(matchOperation, projectionOperation,groupOperation, sortOperation);
        Aggregation aggregation1 = newAggregation(matchOperation,projectionOperation, groupOperation, projectionOperation2, groupOperation1, sortOperation);
        //AggregationResults aggregationResults = mongoTemplate.aggregate(aggregation, "parsedPackets", FlowData.class);
        AggregationResults aggregationResults1 = mongoTemplate.aggregate(aggregation1, "parsedPackets", FlowData.class);
        return aggregationResults1.getMappedResults();
    }

    @Override
    public List<PositionFlowData> getFlow2(long from, long to) {
        MatchOperation matchOperation = match(new Criteria("timestamp").gte(from).lt(to).and("global").is(true));
        GroupOperation groupOperation1 = group("fcs", "hour", "minute", "snifferId")
                .avg("rssi").as("rssi")
                .min("timestamp").as("startTimestamp");
        SortOperation sortOperation = sort(new Sort(Sort.Direction.DESC, "startTimestamp"));
        UnwindOperation unwindOperation = unwind("_id");
        GroupOperation groupOperation2 = group("_id.fcs")
                .push(new BasicDBObject
                        ("hour", "$_id.hour").append
                        ("minute", "$_id.minute").append
                        ("snifferId", "$_id.snifferId").append
                        ("startTimestamp", "$startTimestamp").append
                        ("rssi", "$rssi")).as("data");
        ProjectionOperation projectionOperation = project()
                .andExpression("_id.fcs").as("fcs")
                .andExpression("data").as("data");
        Aggregation aggregation = newAggregation(matchOperation, groupOperation1, sortOperation, groupOperation2);
        AggregationResults aggregationResults = mongoTemplate.aggregate(aggregation, "parsedPackets", PositionFlowData.class);
        return aggregationResults.getMappedResults();
    }
}
