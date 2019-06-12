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

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class CountedPacketsServiceImpl implements CountedPacketsService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<CountedpacketsResult> getResultsByBuilding(String building, long from, long to, String resolution) {
        Instant start = Instant.now();
        MatchOperation matchOperation = match(new Criteria("startTimestamp").gte(from).lt(to).and("buildingId").is(building));
        GroupOperation groupOperation;
        switch (resolution) {
            case "fiveMinute":
                groupOperation = group("year", "month", "dayOfMonth", "hour", "fiveMinute")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "fifteenMinute":
                groupOperation = group("year", "month", "dayOfMonth", "hour", "fifteenMinute")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "thirtyMinute":
                groupOperation = group("year", "month", "dayOfMonth", "hour", "thirtyMinute")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "hour":
                groupOperation = group("year", "month", "dayOfMonth", "hour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "twoHour":
                groupOperation = group("year", "month", "dayOfMonth", "twoHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "fourHour":
                groupOperation = group("year", "month", "dayOfMonth", "fourHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "sixHour":
                groupOperation = group("year", "month", "dayOfMonth", "sixHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "twelveHour":
                groupOperation = group("year", "month", "dayOfMonth", "twelveHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Arguments!");
        }
        SortOperation sortOperation = sort(new Sort(Sort.Direction.ASC, "startTimestamp"));
        Aggregation aggregation = newAggregation(matchOperation, groupOperation, sortOperation);
        AggregationResults results = mongoTemplate.aggregate(aggregation, "countedPackets", CountedpacketsResult.class);
        List<CountedpacketsResult> res = results.getMappedResults();
        res = new ArrayList<>(res);
        LocalDateTime ldt = Instant.ofEpochMilli(from).atZone(ZoneId.of("CET")).toLocalDateTime();
        long startTimestamp = 0;
        long endTimestamp = 0;
        if (ldt.getMinute() % 5 != 0)
            startTimestamp = from + ((5 - (ldt.getMinute() % 5)) * 60000);
        else
            startTimestamp = from;
        ldt = Instant.ofEpochMilli(to).atZone(ZoneId.of("CET")).toLocalDateTime();
        if (ldt.getMinute() % 5 != 0)
            endTimestamp = to - ((5 - (ldt.getMinute() % 5)) * 60000);
        else
            endTimestamp = to;
        int i = 0;
        while (startTimestamp < endTimestamp) {
            if (i < res.size()) {
                CountedpacketsResult cpr = res.get(i);
                if (startTimestamp < cpr.getStartTimestamp()) {
                    CountedpacketsResult countedpacketsResult = new CountedpacketsResult();
                    countedpacketsResult.setAvgEstimatedDevices(0);
                    countedpacketsResult.setTotalPackets(0);
                    countedpacketsResult.setDistinctMacAddresses(0);
                    countedpacketsResult.setDistinctFingerprints(0);
                    countedpacketsResult.setStartTimestamp(startTimestamp);
                    countedpacketsResult.setLocalPackets(0);
                    countedpacketsResult.setGlobalPackets(0);
                    res.add(i, countedpacketsResult);
                }
                switch (resolution) {
                    case "fiveMinute":
                        startTimestamp += 60000 * 5;
                        break;
                    case "fifteenMinute":
                        startTimestamp += 60000 * 15;
                        break;
                    case "thirtyMinute":
                        startTimestamp += 60000 * 30;
                        break;
                    case "hour":
                        startTimestamp += 60000 * 60;
                        break;
                    case "twoHour":
                        startTimestamp += 60000 * 60 * 2;
                        break;
                    case "fourHour":
                        startTimestamp += 60000 * 60 * 4;
                        break;
                    case "sixHour":
                        startTimestamp += 60000 * 60 * 6;
                        break;
                    case "twelveHour":
                        startTimestamp += 60000 * 60 * 12;
                        break;
                    case "day":
                        startTimestamp += 60000 * 60 * 24;
                        break;
                }
                i++;
            } else {
                if (Instant.now().toEpochMilli() <= startTimestamp){
                    Instant stop = Instant.now();
                    System.out.println(Duration.between(start, stop).getNano());
                    return res;
                }
                else {
                    CountedpacketsResult countedpacketsResult = new CountedpacketsResult();
                    countedpacketsResult.setAvgEstimatedDevices(0);
                    countedpacketsResult.setTotalPackets(0);
                    countedpacketsResult.setDistinctMacAddresses(0);
                    countedpacketsResult.setDistinctFingerprints(0);
                    countedpacketsResult.setStartTimestamp(startTimestamp);
                    countedpacketsResult.setLocalPackets(0);
                    countedpacketsResult.setGlobalPackets(0);
                    res.add(i, countedpacketsResult);
                    switch (resolution) {
                        case "fiveMinute":
                            startTimestamp += 60000 * 5;
                            break;
                        case "fifteenMinute":
                            startTimestamp += 60000 * 15;
                            break;
                        case "thirtyMinute":
                            startTimestamp += 60000 * 30;
                            break;
                        case "hour":
                            startTimestamp += 60000 * 60;
                            break;
                        case "twoHour":
                            startTimestamp += 60000 * 60 * 2;
                            break;
                        case "fourHour":
                            startTimestamp += 60000 * 60 * 4;
                            break;
                        case "sixHour":
                            startTimestamp += 60000 * 60 * 6;
                            break;
                        case "twelveHour":
                            startTimestamp += 60000 * 60 * 12;
                            break;
                        case "day":
                            startTimestamp += 60000 * 60 * 24;
                            break;

                    }
                    i++;
                }
            }
        }
        Instant stop = Instant.now();
        System.out.println(Duration.between(start, stop).getNano());
        return res;
    }

    @Override
    public List<CountedpacketsResult> getResultsByRoom(String building, String room, long from, long to, String resolution) {
        Instant start = Instant.now();
        MatchOperation matchOperation = match(new Criteria("startTimestamp").gte(from).lt(to).and("buildingId").is(building).and("roomId").is(room));
        GroupOperation groupOperation;
        switch (resolution) {
            case "fiveMinute":
                groupOperation = group("year", "month", "dayOfMonth", "hour", "fiveMinute")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "fifteenMinute":
                groupOperation = group("year", "month", "dayOfMonth", "hour", "fifteenMinute")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "thirtyMinute":
                groupOperation = group("year", "month", "dayOfMonth", "hour", "thirtyMinute")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "hour":
                groupOperation = group("year", "month", "dayOfMonth", "hour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "twoHour":
                groupOperation = group("year", "month", "dayOfMonth", "twoHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "fourHour":
                groupOperation = group("year", "month", "dayOfMonth", "fourHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "sixHour":
                groupOperation = group("year", "month", "dayOfMonth", "sixHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "twelveHour":
                groupOperation = group("year", "month", "dayOfMonth", "twelveHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Arguments!");
        }
        SortOperation sortOperation = sort(new Sort(Sort.Direction.ASC, "startTimestamp"));
        Aggregation aggregation = newAggregation(matchOperation, groupOperation, sortOperation);
        AggregationResults results = mongoTemplate.aggregate(aggregation, "countedPackets", CountedpacketsResult.class);
        List<CountedpacketsResult> res = results.getMappedResults();
        res = new ArrayList<>(res);
        LocalDateTime ldt = Instant.ofEpochMilli(from).atZone(ZoneId.of("CET")).toLocalDateTime();
        long startTimestamp = 0;
        long endTimestamp = 0;
        if (ldt.getMinute() % 5 != 0)
            startTimestamp = from + ((5 - (ldt.getMinute() % 5)) * 60000);
        else
            startTimestamp = from;
        ldt = Instant.ofEpochMilli(to).atZone(ZoneId.of("CET")).toLocalDateTime();
        if (ldt.getMinute() % 5 != 0)
            endTimestamp = to - ((5 - (ldt.getMinute() % 5)) * 60000);
        else
            endTimestamp = to;
        int i = 0;
        while (startTimestamp < endTimestamp) {
            if (i < res.size()) {
                CountedpacketsResult cpr = res.get(i);
                if (startTimestamp < cpr.getStartTimestamp()) {
                    CountedpacketsResult countedpacketsResult = new CountedpacketsResult();
                    countedpacketsResult.setAvgEstimatedDevices(0);
                    countedpacketsResult.setTotalPackets(0);
                    countedpacketsResult.setDistinctMacAddresses(0);
                    countedpacketsResult.setDistinctFingerprints(0);
                    countedpacketsResult.setStartTimestamp(startTimestamp);
                    countedpacketsResult.setLocalPackets(0);
                    countedpacketsResult.setGlobalPackets(0);
                    res.add(i, countedpacketsResult);
                }
                switch (resolution) {
                    case "fiveMinute":
                        startTimestamp += 60000 * 5;
                        break;
                    case "fifteenMinute":
                        startTimestamp += 60000 * 15;
                        break;
                    case "thirtyMinute":
                        startTimestamp += 60000 * 30;
                        break;
                    case "hour":
                        startTimestamp += 60000 * 60;
                        break;
                    case "twoHour":
                        startTimestamp += 60000 * 60 * 2;
                        break;
                    case "fourHour":
                        startTimestamp += 60000 * 60 * 4;
                        break;
                    case "sixHour":
                        startTimestamp += 60000 * 60 * 6;
                        break;
                    case "twelveHour":
                        startTimestamp += 60000 * 60 * 12;
                        break;
                    case "day":
                        startTimestamp += 60000 * 60 * 24;
                        break;
                }
                i++;
            } else {
                if (Instant.now().toEpochMilli() <= startTimestamp){
                    Instant stop = Instant.now();
                    System.out.println(Duration.between(start, stop).getNano());
                    return res;
                }
                else {
                    CountedpacketsResult countedpacketsResult = new CountedpacketsResult();
                    countedpacketsResult.setAvgEstimatedDevices(0);
                    countedpacketsResult.setTotalPackets(0);
                    countedpacketsResult.setDistinctMacAddresses(0);
                    countedpacketsResult.setDistinctFingerprints(0);
                    countedpacketsResult.setStartTimestamp(startTimestamp);
                    countedpacketsResult.setLocalPackets(0);
                    countedpacketsResult.setGlobalPackets(0);
                    res.add(i, countedpacketsResult);
                    switch (resolution) {
                        case "fiveMinute":
                            startTimestamp += 60000 * 5;
                            break;
                        case "fifteenMinute":
                            startTimestamp += 60000 * 15;
                            break;
                        case "thirtyMinute":
                            startTimestamp += 60000 * 30;
                            break;
                        case "hour":
                            startTimestamp += 60000 * 60;
                            break;
                        case "twoHour":
                            startTimestamp += 60000 * 60 * 2;
                            break;
                        case "fourHour":
                            startTimestamp += 60000 * 60 * 4;
                            break;
                        case "sixHour":
                            startTimestamp += 60000 * 60 * 6;
                            break;
                        case "twelveHour":
                            startTimestamp += 60000 * 60 * 12;
                            break;
                        case "day":
                            startTimestamp += 60000 * 60 * 24;
                            break;

                    }
                    i++;
                }
            }
        }
        Instant stop = Instant.now();
        System.out.println(Duration.between(start, stop).getNano());
        return res;
    }

    @Override
    public List<CountedpacketsResult> getResultsBySniffer(String building, String room, String sniffer, long from, long to, String resolution) {
        Instant start = Instant.now();
        MatchOperation matchOperation = match(new Criteria("startTimestamp").gte(from).lt(to).and("buildingId").is(building).and("roomId").is(room).and("snifferId").is(sniffer));
        GroupOperation groupOperation;
        switch (resolution) {
            case "fiveMinute":
                groupOperation = group("year", "month", "dayOfMonth", "hour", "fiveMinute")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "fifteenMinute":
                groupOperation = group("year", "month", "dayOfMonth", "hour", "fifteenMinute")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "thirtyMinute":
                groupOperation = group("year", "month", "dayOfMonth", "hour", "thirtyMinute")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "hour":
                groupOperation = group("year", "month", "dayOfMonth", "hour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "twoHour":
                groupOperation = group("year", "month", "dayOfMonth", "twoHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "fourHour":
                groupOperation = group("year", "month", "dayOfMonth", "fourHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "sixHour":
                groupOperation = group("year", "month", "dayOfMonth", "sixHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            case "twelveHour":
                groupOperation = group("year", "month", "dayOfMonth", "twelveHour")
                        .sum("totalPackets").as("totalPackets")
                        .sum("globalPackets").as("globalPackets")
                        .sum("localPackets").as("localPackets")
                        .avg("totalDistinctMacAddresses").as("distinctMacAddresses")
                        .avg("totalDistinctFingerprints").as("distinctFingerprints")
                        .min("startTimestamp").as("startTimestamp");
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Arguments!");
        }
        SortOperation sortOperation = sort(new Sort(Sort.Direction.ASC, "startTimestamp"));
        Aggregation aggregation = newAggregation(matchOperation, groupOperation, sortOperation);
        AggregationResults results = mongoTemplate.aggregate(aggregation, "countedPackets", CountedpacketsResult.class);
        List<CountedpacketsResult> res = results.getMappedResults();
        res = new ArrayList<>(res);
        LocalDateTime ldt = Instant.ofEpochMilli(from).atZone(ZoneId.of("CET")).toLocalDateTime();
        long startTimestamp = 0;
        long endTimestamp = 0;
        if (ldt.getMinute() % 5 != 0)
            startTimestamp = from + ((5 - (ldt.getMinute() % 5)) * 60000);
        else
            startTimestamp = from;
        ldt = Instant.ofEpochMilli(to).atZone(ZoneId.of("CET")).toLocalDateTime();
        if (ldt.getMinute() % 5 != 0)
            endTimestamp = to - ((5 - (ldt.getMinute() % 5)) * 60000);
        else
            endTimestamp = to;
        int i = 0;
        while (startTimestamp < endTimestamp) {
            if (i < res.size()) {
                CountedpacketsResult cpr = res.get(i);
                if (startTimestamp < cpr.getStartTimestamp()) {
                    CountedpacketsResult countedpacketsResult = new CountedpacketsResult();
                    countedpacketsResult.setAvgEstimatedDevices(0);
                    countedpacketsResult.setTotalPackets(0);
                    countedpacketsResult.setDistinctMacAddresses(0);
                    countedpacketsResult.setDistinctFingerprints(0);
                    countedpacketsResult.setStartTimestamp(startTimestamp);
                    countedpacketsResult.setLocalPackets(0);
                    countedpacketsResult.setGlobalPackets(0);
                    res.add(i, countedpacketsResult);
                }
                switch (resolution) {
                    case "fiveMinute":
                        startTimestamp += 60000 * 5;
                        break;
                    case "fifteenMinute":
                        startTimestamp += 60000 * 15;
                        break;
                    case "thirtyMinute":
                        startTimestamp += 60000 * 30;
                        break;
                    case "hour":
                        startTimestamp += 60000 * 60;
                        break;
                    case "twoHour":
                        startTimestamp += 60000 * 60 * 2;
                        break;
                    case "fourHour":
                        startTimestamp += 60000 * 60 * 4;
                        break;
                    case "sixHour":
                        startTimestamp += 60000 * 60 * 6;
                        break;
                    case "twelveHour":
                        startTimestamp += 60000 * 60 * 12;
                        break;
                    case "day":
                        startTimestamp += 60000 * 60 * 24;
                        break;
                }
                i++;
            } else {
                if (Instant.now().toEpochMilli() <= startTimestamp){
                    Instant stop = Instant.now();
                    System.out.println(Duration.between(start, stop).getNano());
                    return res;
                }
                else {
                    CountedpacketsResult countedpacketsResult = new CountedpacketsResult();
                    countedpacketsResult.setAvgEstimatedDevices(0);
                    countedpacketsResult.setTotalPackets(0);
                    countedpacketsResult.setDistinctMacAddresses(0);
                    countedpacketsResult.setDistinctFingerprints(0);
                    countedpacketsResult.setStartTimestamp(startTimestamp);
                    countedpacketsResult.setLocalPackets(0);
                    countedpacketsResult.setGlobalPackets(0);
                    res.add(i, countedpacketsResult);
                    switch (resolution) {
                        case "fiveMinute":
                            startTimestamp += 60000 * 5;
                            break;
                        case "fifteenMinute":
                            startTimestamp += 60000 * 15;
                            break;
                        case "thirtyMinute":
                            startTimestamp += 60000 * 30;
                            break;
                        case "hour":
                            startTimestamp += 60000 * 60;
                            break;
                        case "twoHour":
                            startTimestamp += 60000 * 60 * 2;
                            break;
                        case "fourHour":
                            startTimestamp += 60000 * 60 * 4;
                            break;
                        case "sixHour":
                            startTimestamp += 60000 * 60 * 6;
                            break;
                        case "twelveHour":
                            startTimestamp += 60000 * 60 * 12;
                            break;
                        case "day":
                            startTimestamp += 60000 * 60 * 24;
                            break;

                    }
                    i++;
                }
            }
        }
        Instant stop = Instant.now();
        System.out.println(Duration.between(start, stop).getNano());
        return res;
    }
}
