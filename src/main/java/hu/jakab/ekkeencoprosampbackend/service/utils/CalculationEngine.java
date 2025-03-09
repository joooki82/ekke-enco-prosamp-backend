package hu.jakab.ekkeencoprosampbackend.service.utils;

import hu.jakab.ekkeencoprosampbackend.model.Sample;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;


@Component
public class CalculationEngine {


    public BigDecimal calculateSamplingDuration(Sample sample) {
        LocalDateTime start = sample.getStartTime();
        LocalDateTime end = sample.getEndTime();

        Duration duration = Duration.between(start, end);
        return BigDecimal.valueOf(duration.toMinutes()); // Convert to minutes
    }


    public BigDecimal adjustedVolumeFlowByEnvironmentalFactors(Sample sample) {
        BigDecimal standardPressure = new BigDecimal("1013.25"); // Standard atmospheric pressure in hPa
        BigDecimal standardTemperature = new BigDecimal("25.0");  // Reference temperature in Celsius
        BigDecimal kelvinOffset = new BigDecimal("273.15");       // Kelvin conversion

        // Formula: Correction Factor = (P / P0) * ((T0 + 273.15) / (T + 273.15))
        BigDecimal pressureRatio = sample.getPressure().divide(standardPressure, 10, RoundingMode.HALF_UP);
        BigDecimal temperatureRatio = (standardTemperature.add(kelvinOffset))
                .divide(sample.getTemperature().add(kelvinOffset), 10, RoundingMode.HALF_UP);
        BigDecimal correctionFactor = pressureRatio.multiply(temperatureRatio);

        // Adjusted Volume = Volume * Correction Factor
        return sample.getSampleVolumeFlowRate().multiply(correctionFactor).setScale(4, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateAdjustedTotalSampledVolume(Sample sample) {

        if (sample.getSampleVolumeFlowRate() == null) {
            throw new IllegalArgumentException("Sample volume flow rate cannot be null");
        }
        // Convert durationMinutes to BigDecimal and multiply
        return adjustedVolumeFlowByEnvironmentalFactors(sample).multiply(calculateSamplingDuration(sample));
    }



}

