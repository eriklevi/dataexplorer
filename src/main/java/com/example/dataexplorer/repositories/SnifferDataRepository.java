package com.example.dataexplorer.repositories;

import com.example.dataexplorer.entities.SnifferData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SnifferDataRepository extends MongoRepository<SnifferData, String> {
}
