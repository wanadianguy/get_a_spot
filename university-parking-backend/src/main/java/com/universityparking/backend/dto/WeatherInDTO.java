package com.universityparking.backend.dto;

import java.util.List;

public class WeatherInDTO {

    public static class CurrentData {
        public String time;
        public int interval;
        public double temperature_2m;
        public int relative_humidity_2m;
        public double apparent_temperature;
        public int is_day;
        public double precipitation;
        public double rain;
        public double showers;
        public double snowfall;
        public int weather_code;
        public int cloud_cover;
        public double pressure_msl;
        public double surface_pressure;
        public double wind_speed_10m;
        public int wind_direction_10m;
        public double wind_gusts_10m;
    }

    public static class DailyData {
        public List<String> time;
        public List<Double> temperature_2m_max;
        public List<Double> temperature_2m_min;
        public List<Double> apparent_temperature_max;
        public List<Double> apparent_temperature_min;
        public List<String> sunrise;
        public List<String> sunset;
        public List<Double> daylight_duration;
        public List<Double> sunshine_duration;
        public List<Double> precipitation_sum;
        public List<Double> rain_sum;
        public List<Double> showers_sum;
        public List<Double> snowfall_sum;
        public List<Integer> precipitation_probability_max;
        public List<Double> wind_speed_10m_max;
        public List<Double> wind_gusts_10m_max;
        public List<Integer> wind_direction_10m_dominant;
    }

    public double latitude;
    public double longitude;
    public double generationtime_ms;
    public int utc_offset_seconds;
    public String timezone;
    public String timezone_abbreviation;
    public double elevation;

    public static class Units {
        public String time;
        public String interval;
        public String temperature_2m;
        public String relative_humidity_2m;
        public String apparent_temperature;
        public String is_day;
        public String precipitation;
        public String rain;
        public String showers;
        public String snowfall;
        public String weather_code;
        public String cloud_cover;
        public String pressure_msl;
        public String surface_pressure;
        public String wind_speed_10m;
        public String wind_direction_10m;
        public String wind_gusts_10m;
    }

    public Units current_units;

    public CurrentData current;

    public static class DailyUnits {
        public String time;
        public String temperature_2m_max;
        public String temperature_2m_min;
        public String apparent_temperature_max;
        public String apparent_temperature_min;
        public String sunrise;
        public String sunset;
        public String daylight_duration;
        public String sunshine_duration;
        public String precipitation_sum;
        public String rain_sum;
        public String showers_sum;
        public String snowfall_sum;
        public String precipitation_probability_max;
        public String wind_speed_10m_max;
        public String wind_gusts_10m_max;
        public String wind_direction_10m_dominant;
    }

    public DailyUnits daily_units;

    public DailyData daily;
}
