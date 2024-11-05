package com.universityparking.backend.repository;

import com.universityparking.backend.model.Building;
import com.universityparking.backend.model.Site;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BuildingRepository extends MongoRepository<Building, String> {
    List<Building> findAllBySite(Site site);
}
