package com.universityparking.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BuildingCreationDTO extends GPSPointCreationDTO {
    @NotNull
    private String name;
    @NotNull
    private String siteId;
}
