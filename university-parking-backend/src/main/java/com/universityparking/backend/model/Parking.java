package com.universityparking.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "parkings")
public class Parking extends GPSPoint {
    @Id
    private String id;
    @Field("name")
    private String name;
    @Field("site")
    @DBRef
    private Site site;
    @Field("have_captors_installed")
    private boolean isCaptorsInstalled;
    @Field("number_of_places")
    private int fixedNumberOfPlaces;
}
