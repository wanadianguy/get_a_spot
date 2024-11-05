package com.universityparking.backend.controller;

import com.universityparking.backend.dto.ParkingMovementDTO;
import com.universityparking.backend.model.Parking;
import com.universityparking.backend.model.ParkingMovement;
import com.universityparking.backend.service.ParkingMovementService;
import com.universityparking.backend.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/parkingMovementController")
public class ParkingMovementController {

    @Autowired
    ParkingService parkingService;

    @Autowired
    ParkingMovementService parkingMovementService;

    private static final Logger logger = Logger.getLogger(ParkingMovementController.class.getName());
    @GetMapping("")
    public ResponseEntity<List<ParkingMovementDTO>> getAll(){
        parkingMovementService.findAll();
        List<ParkingMovementDTO> parkingMovementDTOList = new ArrayList<>();
        for (ParkingMovement parkingMovement : parkingMovementService.findAll()) {
            ParkingMovementDTO parkingMovementDTO = new ParkingMovementDTO();
            parkingMovementDTO.setId(parkingMovement.getId());
            parkingMovementDTO.setIdParking(parkingMovement.getParking().getId());
            parkingMovementDTO.setTimestamp(parkingMovement.getTimestamp());
            parkingMovementDTO.setType(parkingMovement.getType());
            parkingMovementDTOList.add(parkingMovementDTO);
        }
        return ResponseEntity.ok(parkingMovementDTOList);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ParkingMovementDTO> getParkingMovement(@PathVariable String id) {
        ParkingMovement parkingMovement = parkingMovementService.findById(id);
        if (parkingMovement == null) {
            return ResponseEntity.notFound().build();
        }
        ParkingMovementDTO parkingMovementDTO = new ParkingMovementDTO();
        parkingMovementDTO.setId(parkingMovement.getId());
        parkingMovementDTO.setIdParking(parkingMovement.getParking().getId());
        parkingMovementDTO.setTimestamp(parkingMovement.getTimestamp());
        parkingMovementDTO.setType(parkingMovement.getType());
        return ResponseEntity.ok(parkingMovementDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteParkingMovement(@PathVariable String id) {
        return ResponseEntity.ok(parkingMovementService.deleteParkingMovement(id));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addParkingMovement(@RequestBody ParkingMovementDTO parkingMovementDTO) {
        ParkingMovement parkingMovement = new ParkingMovement();
        parkingMovement.setId(parkingMovementDTO.getId());
        Parking parking = parkingService.findById(parkingMovementDTO.getIdParking());
        parkingMovement.setParking(parking);
        parkingMovement.setTimestamp(parkingMovementDTO.getTimestamp());
        parkingMovement.setType(parkingMovementDTO.getType());
        parkingMovementService.createParkingMovement(parkingMovement);
        return ResponseEntity.ok("Parking movement added successfully");
    }

}
