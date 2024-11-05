package com.universityparking.backend.service;

import com.universityparking.backend.exception.EntityNotFoundException;
import com.universityparking.backend.model.Building;
import com.universityparking.backend.model.Site;
import com.universityparking.backend.repository.BuildingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record BuildingService(
        BuildingRepository buildingRepository
) {

    public List<Building> getAllBuildings() { return buildingRepository.findAll(); }

    public Building getBuildingById(String id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Building.class, id));
    }

    public Building createBuilding(Building building) {
        return buildingRepository.save(building);
    }

    public void deleteBuilding(String id){
        buildingRepository.deleteById(id);
    }
    
}

