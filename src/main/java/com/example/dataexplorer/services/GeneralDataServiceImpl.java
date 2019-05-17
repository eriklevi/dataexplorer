package com.example.dataexplorer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class GeneralDataServiceImpl implements GeneralDataService {

    private static final Logger logger = LoggerFactory.getLogger(GeneralDataServiceImpl.class);

    private final MongoTemplate mongoTemplate;

    public GeneralDataServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public long getTotalParsedPackets() {
        return mongoTemplate.count(new Query(), "parsedPackets");
    }

    @Override
    public long getTotalRawPackets() {
        return mongoTemplate.count(new Query(), "rawPackets");
    }

    @Override
    public long getTotalParsedPacketsBySnifferMac(String snifferMac) {
        return mongoTemplate.count(new Query(Criteria
                .where("snifferMac")
                .is(snifferMac)), "parsedPackets");
    }

    @Override
    public long getTotalRawPacketsBySnifferMac(String snifferMac) {
        return mongoTemplate.count(new Query(Criteria
                .where("snifferMac")
                .is(snifferMac)), "rawPackets");
    }
}
