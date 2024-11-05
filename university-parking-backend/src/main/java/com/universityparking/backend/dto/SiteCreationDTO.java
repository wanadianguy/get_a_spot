package com.universityparking.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SiteCreationDTO extends GPSPointCreationDTO {
    @NotNull
    private String name;
    private String imageId; // nullable
}
