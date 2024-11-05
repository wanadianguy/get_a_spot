package com.universityparking.backend.dto;

import com.universityparking.backend.model.Image;
import com.universityparking.backend.service.SiteService;
import org.springframework.stereotype.Component;

@Component
public record ImageConverters(
        SiteService siteService,
        SiteConverters siteConverters
) {
    public ImageInformationOutDTO convertObjectToInformationOutDto(Image object) {
        ImageInformationOutDTO dto = new ImageInformationOutDTO();
        dto.setId(object.getId());
        return dto;
    }
}

