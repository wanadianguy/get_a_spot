package com.universityparking.backend.repository;

import com.universityparking.backend.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image, String> {
}
