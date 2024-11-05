package com.universityparking.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "sites")
public class Site extends GPSPoint { // Campus
    @Id
    private String id;
    @Field("name")
    private String name;
    @Field("image")
    @DBRef
    private Image image;
    // private Institution institution;
}
