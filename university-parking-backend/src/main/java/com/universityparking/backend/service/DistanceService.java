package com.universityparking.backend.service;

import com.universityparking.backend.model.GPSPoint;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {
    private static final double EARTH_RADIUS = 6371000; // Earth radius in meters

    public double calculateDistance(GPSPoint gpsPoint1, GPSPoint gpsPoint2) {
        // Convert rad to degree
        double lat1 = Math.toRadians(gpsPoint1.getLatitude());
        double lon1 = Math.toRadians(gpsPoint1.getLongitude());
        double lat2 = Math.toRadians(gpsPoint2.getLatitude());
        double lon2 = Math.toRadians(gpsPoint2.getLongitude());

        // Calculate angular deviations
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        // Haversine formula
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distance in meters
        return EARTH_RADIUS * c;
    }
}
