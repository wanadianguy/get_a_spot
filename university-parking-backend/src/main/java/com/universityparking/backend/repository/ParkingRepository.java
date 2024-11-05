package com.universityparking.backend.repository;

import com.universityparking.backend.model.Parking;
import com.universityparking.backend.model.Site;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingRepository extends MongoRepository<Parking, String> {
    List<Parking> findAllBySite(Site site);

}
