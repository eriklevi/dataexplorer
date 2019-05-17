package com.example.dataexplorer.services;

import com.example.dataexplorer.entities.CountedpacketsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class CountedPacketsServiceImpl implements CountedPacketsService{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<CountedpacketsResult> getResultsByBuilding(String building, long from, long to, String resolution) {
        MatchOperation matchOperation = match(new Criteria("startTimestamp").gte(from).lt(to).and("buildingId").is(building));
        GroupOperation groupOperation;
        switch (resolution) {
            case "fiveMinute":
            groupOperation = group("year", "month", "dayOfMonth", "hour", "fiveMinute")
                    .sum("totalPackets").as("totalPackets")
                    .sum("globalPackets").as("globalPackets")
                    .sum("localPackets").as("localPackets")
                    .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                    .min("startTimestamp").as("startTimestamp");
            break;
            case "fifteenMinute":
                groupOperation = group("year", "month", "dayOfMonth", "hour", "fifteenMinute")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
            break;
            case "thirtyMinute":
                groupOperation = group("year", "month", "dayOfMonth", "hour", "thirtyMinute")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
            break;
            case "hour":
                groupOperation = group("year", "month", "dayOfMonth", "hour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
            break;
            case "twoHour":
                groupOperation = group("year", "month", "dayOfMonth", "twoHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
            break;
            case "fourHour":
                groupOperation = group("year", "month", "dayOfMonth", "fourHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
            break;
            case "sixHour":
                groupOperation = group("year", "month", "dayOfMonth", "sixHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
            break;
            case "twelveHour": groupOperation = group("year", "month", "dayOfMonth", "twelveHour")
                    .sum("totalPackets").as("totalPackets")
                    .sum("globalPackets").as("globalPackets")
                    .sum("localPackets").as("localPackets")
                    .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                    .min("startTimestamp").as("startTimestamp");
            break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Arguments!");
        }
        SortOperation sortOperation = sort(new Sort(Sort.Direction.ASC, "startTimestamp"));
        Aggregation aggregation = newAggregation(matchOperation, groupOperation, sortOperation);
        AggregationResults results = mongoTemplate.aggregate(aggregation, "countedPackets", CountedpacketsResult.class);
        List<CountedpacketsResult> res =  results.getMappedResults();
        return res;
    }

    @Override
    public List<CountedpacketsResult> getResultsByRoom(String building, String room, long from, long to, String resolution) {
        MatchOperation matchOperation = match(new Criteria("startTimestamp").gte(from).lt(to).and("buildingId").is(building).and("roomId").is(room));
        GroupOperation groupOperation;
        switch (resolution) {
            case "fiveMinute":
                groupOperation = group("year", "month", "dayOfMonth", "hour", "fiveMinute")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "fifteenMinute":
                groupOperation = group("year", "month", "dayOfMonth", "hour", "fifteenMinute")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "thirtyMinute":
                groupOperation = group("year", "month", "dayOfMonth", "hour", "thirtyMinute")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "hour":
                groupOperation = group("year", "month", "dayOfMonth", "hour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "twoHour":
                groupOperation = group("year", "month", "dayOfMonth", "twoHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "fourHour":
                groupOperation = group("year", "month", "dayOfMonth", "fourHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "sixHour":
                groupOperation = group("year", "month", "dayOfMonth", "sixHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "twelveHour": groupOperation = group("year", "month", "dayOfMonth", "twelveHour")
                    .sum("totalPackets").as("totalPackets")
                    .sum("globalPackets").as("globalPackets")
                    .sum("localPackets").as("localPackets")
                    .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                    .min("startTimestamp").as("startTimestamp");
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Arguments!");
        }
        SortOperation sortOperation = sort(new Sort(Sort.Direction.ASC, "startTimestamp"));
        Aggregation aggregation = newAggregation(matchOperation, groupOperation, sortOperation);
        AggregationResults results = mongoTemplate.aggregate(aggregation, "countedPackets", CountedpacketsResult.class);
        List<CountedpacketsResult> res =  results.getMappedResults();
        return res;
    }

    @Override
    public List<CountedpacketsResult> getResultsBySniffer(String building, String room, String sniffer, long from, long to, String resolution) {
        MatchOperation matchOperation = match(new Criteria("startTimestamp").gte(from).lt(to).and("buildingId").is(building).and("roomId").is(room).and("snifferName").is(sniffer));
        GroupOperation groupOperation;
        switch (resolution) {
            case "fiveMinute":
                groupOperation = group("year", "month", "dayOfMonth", "hour", "fiveMinute")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "fifteenMinute":
                groupOperation = group("year", "month", "dayOfMonth", "hour", "fifteenMinute")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "thirtyMinute":
                groupOperation = group("year", "month", "dayOfMonth", "hour", "thirtyMinute")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "hour":
                groupOperation = group("year", "month", "dayOfMonth", "hour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "twoHour":
                groupOperation = group("year", "month", "dayOfMonth", "twoHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "fourHour":
                groupOperation = group("year", "month", "dayOfMonth", "fourHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "sixHour":
                groupOperation = group("year", "month", "dayOfMonth", "sixHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "twelveHour": groupOperation = group("year", "month", "dayOfMonth", "twelveHour")
                    .sum("totalPackets").as("totalPackets")
                    .sum("globalPackets").as("globalPackets")
                    .sum("localPackets").as("localPackets")
                    .avg("totalEstimatedDevices").as("avgEstimatedDevices")
                    .min("startTimestamp").as("startTimestamp");
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Arguments!");
        }
        SortOperation sortOperation = sort(new Sort(Sort.Direction.ASC, "startTimestamp"));
        Aggregation aggregation = newAggregation(matchOperation, groupOperation, sortOperation);
        AggregationResults results = mongoTemplate.aggregate(aggregation, "countedPackets", CountedpacketsResult.class);
        List<CountedpacketsResult> res =  results.getMappedResults();
        return res;
    }
}
