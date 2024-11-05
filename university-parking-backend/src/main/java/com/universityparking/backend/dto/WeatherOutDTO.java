package com.universityparking.backend.dto;

import lombok.Data;

@Data
public class WeatherOutDTO {
    private double currentTemperature;
    private double minDailyTemperature;
    private double maxDailyTemperature;

    private double apparentCurrentTemperature;
    private double apparentMinDailyTemperature;
    private double apparentMaxDailyTemperature;

    private double cloudCoverProbability;

    private double currentPrecipitation;
    private double precipitationProbability;

    private double currentWindSpeed;
    private double maxWindSpeed;

    private double currentHumidity;
}
