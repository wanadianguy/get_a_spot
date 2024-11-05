package com.universityparking.backend.dto;

import com.universityparking.backend.model.Building;
import com.universityparking.backend.model.Site;
import com.universityparking.backend.service.SiteService;
import org.springframework.stereotype.Component;

@Component
public record BuildingConverters(
        SiteService siteService,
        SiteConverters siteConverters
) {
    public Building convertCreationDtoToObject(BuildingCreationDTO dto) {
        Building object = new Building();
        object.setName(dto.getName());
        object.setSite(this.siteService.getSiteById(dto.getSiteId()));
        object.setLatitude(dto.getLatitude());
        object.setLongitude(dto.getLongitude());
        return object;
    }

    public BuildingOutDTO convertObjectToOutDto(Building object) {
        BuildingOutDTO dto = new BuildingOutDTO();
        dto.setId(object.getId());
        dto.setName(object.getName());
        dto.setSite(this.siteConverters.convertObjectToOutDto(object.getSite()));
        dto.setLatitude(object.getLatitude());
        dto.setLongitude(object.getLongitude());
        return dto;
    }

    public BuildingOfSiteOutDTO convertObjectToOfSiteOutDto(Building object) {
        BuildingOfSiteOutDTO dto = new BuildingOfSiteOutDTO();
        dto.setId(object.getId());
        dto.setName(object.getName());
        dto.setLatitude(object.getLatitude());
        dto.setLongitude(object.getLongitude());
        return dto;
    }
}

