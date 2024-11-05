package com.universityparking.backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BuildingOutDTO extends GPSPointOutDTO {
    private String id;
    private String name;
    private SiteOutDTO site;
}
