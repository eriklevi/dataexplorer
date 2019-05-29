package com.example.dataexplorer.repositories;

import com.example.dataexplorer.entities.Oui;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OuiRepository extends MongoRepository<Oui, String> {
    Optional<Oui> findFirstByOui(String oui);
}
