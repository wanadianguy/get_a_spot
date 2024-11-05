package com.universityparking.backend.repository;

import com.universityparking.backend.model.Site;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SiteRepository extends MongoRepository<Site, String> {
}
