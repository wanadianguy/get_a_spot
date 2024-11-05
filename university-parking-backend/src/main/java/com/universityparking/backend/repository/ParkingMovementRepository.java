package com.universityparking.backend.repository;

import com.universityparking.backend.model.Parking;
import com.universityparking.backend.model.ParkingMovement;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ParkingMovementRepository extends MongoRepository<ParkingMovement, String> {
    List<ParkingMovement> findByParkingAndTimestampAfter(Parking parking, LocalDateTime timestamp);
}
