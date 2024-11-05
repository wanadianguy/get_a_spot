package com.universityparking.backend.dto;

import com.universityparking.backend.model.GPSPoint;
import com.universityparking.backend.model.Parking;
import com.universityparking.backend.service.DistanceService;
import com.universityparking.backend.service.ParkingMovementService;
import com.universityparking.backend.service.ParkingService;
import com.universityparking.backend.service.SiteService;
import org.springframework.stereotype.Component;

@Component
public record ParkingConverters(
        SiteService siteService,
        SiteConverters siteConverters,
        ParkingService parkingService,
        DistanceService distanceService,
        ParkingMovementService parkingMovementService
) {
    public Parking convertCreationDtoToObject(ParkingCreationDTO dto) {
        Parking object = new Parking();
        object.setName(dto.getName());
        object.setSite(this.siteService.getSiteById(dto.getSiteId()));
        object.setLatitude(dto.getLatitude());
        object.setLongitude(dto.getLongitude());
        object.setCaptorsInstalled(dto.isCaptorsInstalled());
        object.setFixedNumberOfPlaces(dto.getFixedNumberOfPlaces());
        return object;
    }

    public ParkingOutDTO convertObjectToOutDto(Parking object) {
        ParkingOutDTO dto = new ParkingOutDTO();
        dto.setId(object.getId());
        dto.setName(object.getName());
        dto.setSite(this.siteConverters.convertObjectToOutDto(object.getSite()));
        dto.setLatitude(object.getLatitude());
        dto.setLongitude(object.getLongitude());
        dto.setCaptorsInstalled(object.isCaptorsInstalled());
        dto.setFixedNumberOfPlaces(object.getFixedNumberOfPlaces());
        if (object.isCaptorsInstalled()) {
            dto.setDynamicNumberOfPlaces(this.parkingMovementService.getMaxUsedParkingPlaces(object, 24*7));
            dto.setNumberOfUsedPlaces(this.parkingMovementService.getCurrentlyUsedParkingPlaces(object));
        }
        return dto;
    }

    public ParkingOfSiteOutDTO convertObjectToOfSiteOutDto(Parking object) {
        ParkingOfSiteOutDTO dto = new ParkingOfSiteOutDTO();
        dto.setId(object.getId());
        dto.setName(object.getName());
        dto.setLatitude(object.getLatitude());
        dto.setLongitude(object.getLongitude());
        dto.setCaptorsInstalled(object.isCaptorsInstalled());
        dto.setFixedNumberOfPlaces(object.getFixedNumberOfPlaces());
        if (object.isCaptorsInstalled()) {
            dto.setDynamicNumberOfPlaces(this.parkingMovementService.getMaxUsedParkingPlaces(object, 24*7));
            dto.setNumberOfUsedPlaces(this.parkingMovementService.getCurrentlyUsedParkingPlaces(object));
        }
        return dto;
    }

    public ParkingOfSiteDistanceOutDTO convertObjectToDistanceDTO(Parking object, GPSPoint gpsPoint) {
        ParkingOfSiteDistanceOutDTO dto = new ParkingOfSiteDistanceOutDTO();
        dto.setId(object.getId());
        dto.setName(object.getName());
        dto.setLatitude(object.getLatitude());
        dto.setLongitude(object.getLongitude());
        dto.setDistanceFromBuilding(this.distanceService.calculateDistance(object, gpsPoint));
        dto.setCaptorsInstalled(object.isCaptorsInstalled());
        dto.setFixedNumberOfPlaces(object.getFixedNumberOfPlaces());
        if (object.isCaptorsInstalled()) {
            dto.setDynamicNumberOfPlaces(this.parkingMovementService.getMaxUsedParkingPlaces(object, 24*7));
            dto.setNumberOfUsedPlaces(this.parkingMovementService.getCurrentlyUsedParkingPlaces(object));
        }
        return dto;
    }
}
