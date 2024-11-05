package com.universityparking.backend.controller;

import com.universityparking.backend.dto.*;
import com.universityparking.backend.model.Building;
import com.universityparking.backend.model.Parking;
import com.universityparking.backend.service.BuildingService;
import com.universityparking.backend.service.SiteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/building")
public record BuildingController(
        BuildingService buildingService,
        BuildingConverters buildingConverters,
        SiteService siteService,
        ParkingConverters parkingConverters
) {
    @GetMapping("")
    public ResponseEntity<List<BuildingOutDTO>> getAllBuildings() {
        List<Building> buildings = buildingService.getAllBuildings();
        List<BuildingOutDTO> buildingOutDtos = buildings.stream()
                .map(buildingConverters::convertObjectToOutDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(buildingOutDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingOutDTO> getBuildingById(
            @PathVariable String id
    ) {
        Building building = buildingService.getBuildingById(id);
        BuildingOutDTO buildingOutDto = buildingConverters.convertObjectToOutDto(building);
        return ResponseEntity.ok(buildingOutDto);
    }

    @GetMapping("/{id}/parkings")
    public ResponseEntity<List<ParkingOfSiteDistanceOutDTO>> getParkingsDistanceByBuildingId(
            @PathVariable String id
    ) {
        // Get building id
        Building building = buildingService.getBuildingById(id);

        // Get Site id
        String siteId = building.getSite().getId();

        // Get
        List<Parking> parkingsList = siteService.getAllParkingsBySiteId(siteId);

        // Utiliser stream pour mapper chaque Parking à ParkingOutDistanceDTO
        List<ParkingOfSiteDistanceOutDTO> parkingListToReturn = parkingsList.stream()
                .map(parking -> parkingConverters.convertObjectToDistanceDTO(parking, building))
                .collect(Collectors.toList());

        // Retourner la liste des DTO avec les distances calculées
        return ResponseEntity.ok(parkingListToReturn);
    }


    @PostMapping("")
    public ResponseEntity<BuildingOutDTO> createBuilding(
            @Valid @RequestBody BuildingCreationDTO buildingCreationDTO
    ) {
        Building buildingToCreate = buildingConverters.convertCreationDtoToObject(buildingCreationDTO);
        Building createdBuilding = buildingService.createBuilding(buildingToCreate);
        BuildingOutDTO createdBuildingOutDto = buildingConverters.convertObjectToOutDto(createdBuilding);
        return new ResponseEntity<>(createdBuildingOutDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuilding(
            @PathVariable String id
    ) {
        this.buildingService.deleteBuilding(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
