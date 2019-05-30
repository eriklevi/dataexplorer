package com.example.dataexplorer.services;

import com.example.dataexplorer.entities.DeviceData;
import com.example.dataexplorer.entities.DeviceInfo;
import com.example.dataexplorer.entities.Oui;
import com.example.dataexplorer.repositories.OuiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceTrackingServiceImpl implements DeviceTrackingService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceTrackingServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private OuiRepository ouiRepository;

    @Override
    public List<DeviceData> getDeviceDataByDayAndMacAddress(long from, long to, String mac){
        long[] timestamps = new long[4096];
        List<DeviceData> list = mongoTemplate.find(
                new Query(
                        Criteria.where("deviceMac").is(mac)
                        .and("timestamp").gte(from).lte(to))
                        .with(new Sort(Sort.Direction.ASC, "timestamp"))
                , DeviceData.class
                , "parsedPackets");
         list = list.stream()
                .filter(p -> {
                    if(timestamps[p.getSequenceNumber()] == 0){
                        timestamps[p.getSequenceNumber()] = p.getTimestamp();
                        return true;
                    }
                    else{
                        if((p.getTimestamp() - timestamps[p.getSequenceNumber()]) < 10000){
                            timestamps[p.getSequenceNumber()] = p.getTimestamp();
                            return false;
                        }
                        else{
                            timestamps[p.getSequenceNumber()] = p.getTimestamp();
                            return true;
                        }
                    }})
                .collect(Collectors.toList());
         return list;
    }

    @Override
    public List<DeviceInfo> getDistinctMacByDay(long from, long to) {
        List<String> list = mongoTemplate.findDistinct(
                new Query(
                        Criteria
                                .where("timestamp").gte(from).lte(to)
                                .and("global").is(true)
                )
                , "deviceMac"
                , "parsedPackets"
                , String.class
        );
        return list.stream()
                .map( item -> {
                    DeviceInfo di = new DeviceInfo();
                    di.setMac(item);
                    Optional<Oui> optionalOui = ouiRepository.findFirstByOui(item.substring(0, 8));
                    di.setOuiName(optionalOui.isPresent()?optionalOui.get().getShortName():"Unknown");
                    di.setOuiCompleteName(optionalOui.isPresent()?optionalOui.get().getCompleteName():"Unknown");
                    return di;
                })
                .sorted(Comparator.comparing(DeviceInfo::getOuiName))
                .collect(Collectors.toList());
    }
}
