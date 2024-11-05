package com.universityparking.backend.service;

import com.universityparking.backend.repository.ParkingRepository;
import org.springframework.stereotype.Service;

@Service
public record UserService(
        ParkingRepository parkingRepository
) {
    public UserService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }
}
