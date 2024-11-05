package com.universityparking.backend.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class GPSPoint {
    @Field("latitude")
    private double latitude;
    @Field("longitude")
    private double longitude;
}
