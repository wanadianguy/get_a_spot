package com.universityparking.backend.service;

import com.universityparking.backend.exception.EntityNotFoundException;
import com.universityparking.backend.model.Parking;
import com.universityparking.backend.repository.ParkingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record ParkingService(
        ParkingRepository parkingRepository
) {

    public List<Parking> findAll() {
        return parkingRepository.findAll();
    }

    public Parking findById(String id) {
        return parkingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Parking.class, id));
    }

    public Parking createParking(Parking parking) {
        return parkingRepository.save(parking);
    }

    public void deleteParking(String id) {
        parkingRepository.deleteById(id);
    }
}
