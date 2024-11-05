package com.universityparking.backend.dto;

import com.universityparking.backend.model.ParkingMovementType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParkingMovementDTO {

    private String id;
    private String idParking;
    private LocalDateTime timestamp;
    private ParkingMovementType type;


}
