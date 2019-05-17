package com.example.dataexplorer.repositories;

import com.example.dataexplorer.entities.CountedPacket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CountedPacketsRepository extends MongoRepository <CountedPacket, String> {
}
