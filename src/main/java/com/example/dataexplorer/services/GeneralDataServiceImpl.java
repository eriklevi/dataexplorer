package com.example.dataexplorer.services;

import com.example.dataexplorer.entities.*;
import com.example.dataexplorer.repositories.CountedPacketsRepository;
import com.example.dataexplorer.repositories.SnifferDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class GeneralDataServiceImpl implements GeneralDataService {

    private static final Logger logger = LoggerFactory.getLogger(GeneralDataServiceImpl.class);

    private final MongoTemplate mongoTemplate;
    private final SnifferDataRepository snifferDataRepository;
    private final CountedPacketsRepository countedPacketsRepository;

    public GeneralDataServiceImpl(MongoTemplate mongoTemplate, SnifferDataRepository snifferDataRepository, CountedPacketsRepository countedPacketsRepository) {
        this.mongoTemplate = mongoTemplate;
        this.snifferDataRepository = snifferDataRepository;
        this.countedPacketsRepository = countedPacketsRepository;
    }

    @Override
    public PacketsStats getParsedPacketsStats() {
        PacketsStats ps = new PacketsStats();
        ps.setGlobal(mongoTemplate.count(new Query(Criteria.where("global").is(true)), "parsedPackets"));
        ps.setLocal(mongoTemplate.count(new Query(Criteria.where("global").is(false)), "parsedPackets"));
        return ps;
    }

    @Override
    public PacketsStats getParsedPacketsStatsBySnifferId(String id) {
        Optional<SnifferData> optionalSnifferData = this.snifferDataRepository.findById(id);
        if(optionalSnifferData.isPresent()){
            SnifferData snifferData = optionalSnifferData.get();
            PacketsStats ps = new PacketsStats();
            ps.setGlobal(mongoTemplate.count(new Query(Criteria.where("global").is(true).and("snifferName").is(snifferData.getName()).and("snifferBuilding").is(snifferData.getBuildingName()).and("snifferRoom").is(snifferData.getRoomName())), "parsedPackets"));
            ps.setLocal(mongoTemplate.count(new Query(Criteria.where("global").is(false).and("snifferName").is(snifferData.getName()).and("snifferBuilding").is(snifferData.getBuildingName()).and("snifferRoom").is(snifferData.getRoomName())), "parsedPackets"));
            return ps;
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No sniffer found with given ID");
        }
    }

    @Override
    public CountedPacket getLastDeviceNumberEstimationById(String id) {
        return this.countedPacketsRepository.findTopBySnifferIdOrderByStartTimestampDesc(id);
    }

    @Override
    public MeanResult getMeanestimation(String id, long timestamp) {
        LocalDateTime ldt = Instant.ofEpochMilli(timestamp).atZone(ZoneId.of("CET")).toLocalDateTime();
        MatchOperation matchOperation = match(new Criteria("snifferId").is(id)
                .and("dayOfWeek").is(ldt.getDayOfWeek().getValue())
                .and("hour").is(ldt.getHour())
                .and("fiveMinute").is((ldt.getMinute()/5)+1));
        GroupOperation groupOperation = group("snifferId", "dayOfWeek", "hour", "fiveMinute")
                .avg("totalestimatedDevices").as("mean");
        ProjectionOperation projectionOperation = project("mean");
        Aggregation aggregation1 = newAggregation(matchOperation, groupOperation);
        AggregationResults aggregationResults1 = mongoTemplate.aggregate(aggregation1, "parsedPackets", MeanResult.class);
        return (MeanResult)aggregationResults1.getMappedResults().get(0);
    }
}
