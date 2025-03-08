package hu.jakab.ekkeencoprosampbackend.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CalculationService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public long calculateSamplingDuration(String startDateTime, String endDateTime) {
        LocalDateTime start = LocalDateTime.parse(startDateTime, FORMATTER);
        LocalDateTime end = LocalDateTime.parse(endDateTime, FORMATTER);

        Duration duration = Duration.between(start, end);
        return duration.toMinutes(); // Convert to minutes
    }

    public double calculateTotalSampledVolume(double flowRate, long durationMinutes) {
        return flowRate * durationMinutes; // Volume = Flow Rate * Time
    }

    public double adjustForEnvironmentalFactors(double volume, double temperature, double pressure) {
        double standardPressure = 1013.25; // Standard atmospheric pressure in hPa
        double standardTemperature = 25.0; // Reference temperature in Celsius

        double correctionFactor = (pressure / standardPressure) * ((standardTemperature + 273.15) / (temperature + 273.15));
        return volume * correctionFactor;
    }


}

