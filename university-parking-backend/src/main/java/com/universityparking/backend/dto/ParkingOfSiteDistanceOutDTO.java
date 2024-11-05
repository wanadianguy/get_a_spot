package com.universityparking.backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParkingOfSiteDistanceOutDTO extends GPSPointOutDTO {
    private String id;
    private String name;
    private Integer dynamicNumberOfPlaces; // nullable
    private Integer numberOfUsedPlaces; // nullable
    private double distanceFromBuilding;
    private boolean captorsInstalled;
    private Integer fixedNumberOfPlaces; // nullable
}
