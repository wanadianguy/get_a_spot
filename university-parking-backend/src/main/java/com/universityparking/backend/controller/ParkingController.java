package com.universityparking.backend.controller;

import com.universityparking.backend.dto.ParkingConverters;
import com.universityparking.backend.dto.ParkingCreationDTO;
import com.universityparking.backend.dto.ParkingOutDTO;
import com.universityparking.backend.model.Parking;
import com.universityparking.backend.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parking")
public record ParkingController(
        ParkingService parkingService,
        ParkingConverters parkingConverters
) {

    @GetMapping("")
    public ResponseEntity<List<ParkingOutDTO>> getAllParkings() {
        List<Parking> parkings = parkingService.findAll();
        List<ParkingOutDTO> parkingOutDTOs = parkings.stream()
                .map(parkingConverters::convertObjectToOutDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(parkingOutDTOs); // 200 OK with list of ParkingOutDTOs
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingOutDTO> getParkingById(
            @PathVariable String id) {
        Parking parking = parkingService.findById(id);
        ParkingOutDTO parkingOutDTO = parkingConverters.convertObjectToOutDto(parking);
        return ResponseEntity.ok(parkingOutDTO); // 200 OK with ParkingOutDTO
    }

    @PostMapping("")
    public ResponseEntity<ParkingOutDTO> createParking(
            @Valid @RequestBody ParkingCreationDTO parkingCreationDTO) {
        Parking parkingToCreate = parkingConverters.convertCreationDtoToObject(parkingCreationDTO);
        Parking createdParking = parkingService.createParking(parkingToCreate);
        ParkingOutDTO createdParkingOutDTO = parkingConverters.convertObjectToOutDto(createdParking);
        return new ResponseEntity<>(createdParkingOutDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParking(
            @PathVariable String id) {
        this.parkingService.deleteParking(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
