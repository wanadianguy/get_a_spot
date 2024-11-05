package com.universityparking.backend.controller;

import com.universityparking.backend.dto.*;
import com.universityparking.backend.model.Building;
import com.universityparking.backend.model.Parking;
import com.universityparking.backend.model.Site;
import com.universityparking.backend.service.SiteService;
import com.universityparking.backend.service.WeatherService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/site")
public record SiteController(
        SiteService siteService,
        SiteConverters siteConverters,
        BuildingConverters buildingConverters,
        ParkingConverters parkingConverters,
        WeatherService weatherService
) {
    @GetMapping("")
    public ResponseEntity<List<SiteOutDTO>> getAllSites() {
        List<Site> sites = siteService.getAllSites();
        List<SiteOutDTO> siteOutDtos = sites.stream()
                .map(siteConverters::convertObjectToOutDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(siteOutDtos); // 200 OK with list of SiteOutDTOs
    }


    @GetMapping("/{id}")
    public ResponseEntity<SiteOutDTO> getSiteById(
            @PathVariable String id
    ) {
        Site site = siteService.getSiteById(id);
        SiteOutDTO siteOutDto = siteConverters.convertObjectToOutDto(site);
        return ResponseEntity.ok(siteOutDto); // 200 OK with SiteOutDTO
    }


    @PostMapping("")
    public ResponseEntity<SiteOutDTO> createSite(
            @Valid @RequestBody SiteCreationDTO siteCreationDTO
    ) {
        Site siteToCreate = siteConverters.convertCreationDtoToObject(siteCreationDTO);
        Site createdSite = siteService.createSite(siteToCreate);
        SiteOutDTO createdSiteOutDto = siteConverters.convertObjectToOutDto(createdSite);
        return new ResponseEntity<>(createdSiteOutDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(
            @PathVariable String id
    ) {
        this.siteService.deleteSite(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/{id}/weather")
    public ResponseEntity<WeatherOutDTO> getWeatherBySiteId(
            @PathVariable String id
    ) {
        Site site = this.siteService.getSiteById(id);
        WeatherOutDTO weatherOutDTO = this.weatherService.getWeatherOfGpsPoint(site);
        return ResponseEntity.ok(weatherOutDTO); // 200 OK with WeatherOutDTO
    }

    @GetMapping("/{id}/buildings")
    public ResponseEntity<List<BuildingOfSiteOutDTO>> getBuildingsBySiteId(
            @PathVariable String id
    ) {
        List<Building> buildings = siteService.getAllBuildingsBySiteId(id);
        List<BuildingOfSiteOutDTO> BuildingOfSiteOutDTO = buildings.stream()
                .map(buildingConverters::convertObjectToOfSiteOutDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(BuildingOfSiteOutDTO); // 200 OK with list of BuildingOfSiteOutDTO
    }

    @GetMapping("/{id}/parkings")
    public ResponseEntity<List<ParkingOfSiteOutDTO>> getParkingsBySiteId(
            @PathVariable String id
    ) {
        List<Parking> parkings = siteService.getAllParkingsBySiteId(id);
        List<ParkingOfSiteOutDTO> ParkingOfSiteOutDTO = parkings.stream()
                .map(parkingConverters::convertObjectToOfSiteOutDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ParkingOfSiteOutDTO); // 200 OK with list of ParkingOfSiteOutDTO
    }
}
