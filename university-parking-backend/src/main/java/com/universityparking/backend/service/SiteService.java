package com.universityparking.backend.service;

import com.universityparking.backend.exception.EntityNotFoundException;
import com.universityparking.backend.model.Building;
import com.universityparking.backend.model.Parking;
import com.universityparking.backend.model.Site;
import com.universityparking.backend.repository.BuildingRepository;
import com.universityparking.backend.repository.ParkingRepository;
import com.universityparking.backend.repository.SiteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record SiteService(
        SiteRepository siteRepository,
        ParkingRepository parkingRepository,
        BuildingRepository buildingRepository
) {

    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    public Site getSiteById(String id) {
        return siteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Site.class, id));
    }

    public List<Building> getAllBuildingsBySiteId(String siteId){
        Site site = this.getSiteById(siteId);
        return buildingRepository.findAllBySite(site);
    }

    public List<Parking> getAllParkingsBySiteId(String siteId){
        Site site = this.getSiteById(siteId);
        return parkingRepository.findAllBySite(site);
    }

    public Site createSite(Site site) {
        return siteRepository.save(site);
    }

    public void deleteSite(String id) {
        siteRepository.deleteById(id);
    }

}
