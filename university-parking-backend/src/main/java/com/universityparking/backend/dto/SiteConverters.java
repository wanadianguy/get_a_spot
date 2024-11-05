package com.universityparking.backend.dto;

import com.universityparking.backend.model.Site;
import com.universityparking.backend.repository.ImageRepository;
import com.universityparking.backend.service.ImageService;
import org.springframework.stereotype.Component;

@Component
public record SiteConverters(
        ImageRepository imageRepository,
        ImageService imageService
) {
    public Site convertCreationDtoToObject(SiteCreationDTO dto) {
        Site object = new Site();
        object.setName(dto.getName());
        object.setLatitude(dto.getLatitude());
        object.setLongitude(dto.getLongitude());
        if (dto.getImageId() != null) {
            object.setImage(this.imageService.getImageById(dto.getImageId()));
        }
        else {
            object.setImage(null);
        }
        return object;
    }

    public SiteOutDTO convertObjectToOutDto(Site object) {
        SiteOutDTO dto = new SiteOutDTO();
        dto.setId(object.getId());
        dto.setName(object.getName());
        dto.setLatitude(object.getLatitude());
        dto.setLongitude(object.getLongitude());
        if (object.getImage() != null) {
            dto.setImageId(object.getImage().getId());
        }
        return dto;
    }
}
