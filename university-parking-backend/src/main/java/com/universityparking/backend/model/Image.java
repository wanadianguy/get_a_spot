package com.universityparking.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "images")
public class Image {
    @Id
    private String id;
    @Field("data")
    private byte[] data;
}
