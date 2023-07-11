package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.dto.OpenWeatherDto;
import com.reservation_restaurants_service.dto.WeatherDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    public WeatherDto getWeather(double lat, double lon) {
        RestTemplate restTemplate = new RestTemplate();
        String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid=bb0f178d94ee5ae0ab8a7ae0457ce5f1";
        ResponseEntity<OpenWeatherDto> response = restTemplate.getForEntity(weatherUrl, OpenWeatherDto.class, lat, lon);
        if (response.getBody() == null) {
            return null;
        }
        if (response.getBody().getMain() == null) {
            return null;
        }
        if (response.getBody().getWeather() == null) {
            return null;
        }
        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setTemperature(response.getBody().getMain().getTemp());
        weatherDto.setTypeOfWeather(response.getBody().getWeather().stream().findFirst().get().getDescription());
        return weatherDto;
    }
}