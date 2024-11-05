package com.universityparking.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "parking_movements")
public class ParkingMovement {
    @Id
    private String id;
    @Field("parking")
    @DBRef
    private Parking parking;
    @Field("timestamp")
    private LocalDateTime timestamp;
    @Field("type")
    private ParkingMovementType type;
}

