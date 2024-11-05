package com.universityparking.backend.service;

import com.universityparking.backend.dto.WeatherConverters;
import com.universityparking.backend.dto.WeatherOutDTO;
import com.universityparking.backend.model.GPSPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record WeatherService(
        WeatherConverters weatherConverters
) {
    public WeatherOutDTO getWeatherOfGpsPoint(GPSPoint gpsPoint) {
        String apiUrlBase = "https://api.open-meteo.com/v1/forecast";
        String currentParams = "temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m";
        String dailyParams = "temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset,daylight_duration,sunshine_duration,precipitation_sum,rain_sum,showers_sum,snowfall_sum,precipitation_probability_max,wind_speed_10m_max,wind_gusts_10m_max,wind_direction_10m_dominant";
        int forecastDays = 1;

        String apiUrl = String.format("%s?latitude=%s&longitude=%s&current=%s&daily=%s&forecast_days=%s",
                apiUrlBase, gpsPoint.getLatitude(), gpsPoint.getLongitude(), currentParams, dailyParams, forecastDays);

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(apiUrl, String.class);

        return this.weatherConverters.convertJsonToOutDto(result);
    }
}
