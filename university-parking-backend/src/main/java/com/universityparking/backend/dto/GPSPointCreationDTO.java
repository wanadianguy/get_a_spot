package com.universityparking.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GPSPointCreationDTO {
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
}
