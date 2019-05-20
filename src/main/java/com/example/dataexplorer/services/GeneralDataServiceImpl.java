package com.example.dataexplorer.services;

import com.example.dataexplorer.entities.PacketsStats;
import com.example.dataexplorer.entities.SnifferData;
import com.example.dataexplorer.repositories.SnifferDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GeneralDataServiceImpl implements GeneralDataService {

    private static final Logger logger = LoggerFactory.getLogger(GeneralDataServiceImpl.class);

    private final MongoTemplate mongoTemplate;
    private final SnifferDataRepository snifferDataRepository;

    public GeneralDataServiceImpl(MongoTemplate mongoTemplate, SnifferDataRepository snifferDataRepository) {
        this.mongoTemplate = mongoTemplate;
        this.snifferDataRepository = snifferDataRepository;
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
            ps.setGlobal(mongoTemplate.count(new Query(Criteria.where("global").is(true).and("snifferName").is(snifferData.getName()).and("buildingName").is(snifferData.getBuildingName()).and("roomName").is(snifferData.getRoomName())), "parsedPackets"));
            ps.setLocal(mongoTemplate.count(new Query(Criteria.where("global").is(false).and("snifferName").is(snifferData.getName()).and("buildingName").is(snifferData.getBuildingName()).and("roomName").is(snifferData.getRoomName())), "parsedPackets"));
            return ps;
        }
    }
}
