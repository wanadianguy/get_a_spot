package com.universityparking.backend.service;

import com.universityparking.backend.model.Parking;
import com.universityparking.backend.model.ParkingMovement;
import com.universityparking.backend.model.ParkingMovementType;
import com.universityparking.backend.repository.ParkingMovementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public record ParkingMovementService(
        ParkingMovementRepository parkingMovementRepository
) {
    public ParkingMovement createParkingMovement(ParkingMovement parkingMovement) {
        return parkingMovementRepository.save(parkingMovement);
    }

    public int getCurrentlyUsedParkingPlaces(Parking parking) {
        List<ParkingMovement> movements = parkingMovementRepository.findByParkingAndTimestampAfter( // We use only movements of the day to have lighter calculations and avoid problems that are amplified if there are already cars in the car park when the sensors are installed
                parking,
                LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
        );

        int entryCount = 0;
        int exitCount = 0;

        for (ParkingMovement movement : movements) {
            if (movement.getType() == ParkingMovementType.ENTRY) {
                entryCount++;
            } else if (movement.getType() == ParkingMovementType.EXIT) {
                exitCount++;
            }
        }

        // Calculate the currently used parking spaces
        int currentlyUsedSpaces = entryCount - exitCount;

        // Ensure that the count doesn't go below zero
        return Math.max(0, currentlyUsedSpaces);
    }

    public int getMaxUsedParkingPlaces(Parking parking, int consideredPreviousHours) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusHours(consideredPreviousHours);
        List<ParkingMovement> movements = parkingMovementRepository.findByParkingAndTimestampAfter(parking, startTime);

        // Classify ParkingMovement by dateTime
        movements.sort(Comparator.comparing(ParkingMovement::getTimestamp));

        int maxUsedSpaces = 0;
        int currentlyUsedSpaces = 0;

        for (ParkingMovement movement : movements) {
            if (movement.getType() == ParkingMovementType.ENTRY) {
                currentlyUsedSpaces++;
            } else if (movement.getType() == ParkingMovementType.EXIT) {
                currentlyUsedSpaces--;
            }

            // Update the maximum number of parking spaces used simultaneously
            maxUsedSpaces = Math.max(maxUsedSpaces, currentlyUsedSpaces);
        }

        return maxUsedSpaces;
    }

    public List<ParkingMovement> findAll() {
        return parkingMovementRepository.findAll();
    }

    public String deleteParkingMovement(String id) {
        try {
            parkingMovementRepository.deleteById(id);
            return "Parking movement deleted successfully";
        } catch (Exception e) {
            return "Parking movement not found";
        }
    }

    public ParkingMovement findById(String id) {
        return parkingMovementRepository.findById(id).orElse(null);
    }
}

