package com.example.dataexplorer.services;

import com.example.dataexplorer.entities.DeviceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class DeviceTrackingServiceImpl implements DeviceTrackingService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceTrackingServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<DeviceData> getDeviceDataByDayAndMacAddress(int year, int month, int day, String mac){
        List<DeviceData> list = mongoTemplate.find(new Query(Criteria.where("deviceMac").is(mac)
                .and("year").is(year)
                .and("month").is(month)
                .and("dayOfMonth").is(day))
                        .with(new Sort(Sort.Direction.ASC, "timestamp"))
                , DeviceData.class
                , "parsedPackets");
        list.stream().
        for( DeviceData dd : list){

        }
    }
}
