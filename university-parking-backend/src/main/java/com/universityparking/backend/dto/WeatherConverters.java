package com.universityparking.backend.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.universityparking.backend.exception.ExternalApiException;
import org.springframework.stereotype.Component;

@Component
public record WeatherConverters() {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public WeatherOutDTO convertJsonToOutDto(String jsonResponse) {
        try {
            WeatherInDTO weatherInDTO = objectMapper.readValue(jsonResponse, WeatherInDTO.class);
            return convertWeatherInDtoToOutDto(weatherInDTO);
        } catch (Exception e) {
            throw new ExternalApiException("Weather", e.getMessage());
        }
    }

    public WeatherOutDTO convertWeatherInDtoToOutDto(WeatherInDTO weatherInDTO) {
        WeatherOutDTO weatherOutDTO = new WeatherOutDTO();
        weatherOutDTO.setCurrentTemperature(weatherInDTO.current.temperature_2m);
        weatherOutDTO.setMaxDailyTemperature(weatherInDTO.daily.temperature_2m_max.get(0));
        weatherOutDTO.setMinDailyTemperature(weatherInDTO.daily.temperature_2m_min.get(0));
        weatherOutDTO.setApparentCurrentTemperature(weatherInDTO.current.apparent_temperature);
        weatherOutDTO.setApparentMaxDailyTemperature(weatherInDTO.daily.apparent_temperature_max.get(0));
        weatherOutDTO.setApparentMinDailyTemperature(weatherInDTO.daily.apparent_temperature_min.get(0));
        weatherOutDTO.setCloudCoverProbability((double) weatherInDTO.current.cloud_cover / 100);
        weatherOutDTO.setCurrentPrecipitation(weatherInDTO.current.precipitation);
        weatherOutDTO.setPrecipitationProbability((double) weatherInDTO.daily.precipitation_probability_max.get(0) / 100);
        weatherOutDTO.setCurrentWindSpeed(weatherInDTO.current.wind_speed_10m);
        weatherOutDTO.setMaxWindSpeed(weatherInDTO.daily.wind_speed_10m_max.get(0));
        weatherOutDTO.setCurrentHumidity(weatherInDTO.current.relative_humidity_2m);
        return weatherOutDTO;
    }
}
