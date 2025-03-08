package hu.jakab.ekkeencoprosampbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

class CalculationServiceTest {

    private CalculationService calculationService;

    @BeforeEach
    void setUp() {
        calculationService = new CalculationService();
    }

    @Test
    void testCalculateSamplingDuration_SameDay() {
        long duration = calculationService.calculateSamplingDuration("2024-02-15 08:30:00", "2024-02-15 09:30:00");
        assertEquals(60, duration, "Duration should be 60 minutes");
    }

    @Test
    void testCalculateSamplingDuration_MultipleDays() {
        long duration = calculationService.calculateSamplingDuration("2024-02-15 08:30:00", "2024-02-16 09:30:00");
        assertEquals(1500, duration, "Duration should be 1500 minutes (25 hours)");
    }

    @Test
    void testCalculateSamplingDuration_InvalidFormat() {
        Exception exception = assertThrows(DateTimeParseException.class, () ->
                calculationService.calculateSamplingDuration("2024-02-15T08:30:00", "2024-02-15T09:30:00")
        );
        assertTrue(exception.getMessage().contains("could not be parsed"), "Should throw DateTimeParseException");
    }

    @Test
    void testCalculateSamplingDuration_EndBeforeStart() {
        long duration = calculationService.calculateSamplingDuration("2024-02-15 09:30:00", "2024-02-15 08:30:00");
        assertEquals(-60, duration, "Duration should be negative if end time is before start time");
    }

    @Test
    void testCalculateTotalSampledVolume_ValidInputs() {
        double volume = calculationService.calculateTotalSampledVolume(0.74367, 1500);
        assertEquals(1115.505, volume, 0.001, "Total volume should be correct");
    }

    @Test
    void testCalculateTotalSampledVolume_ZeroFlowRate() {
        double volume = calculationService.calculateTotalSampledVolume(0, 1500);
        assertEquals(0, volume, "Volume should be 0 if flow rate is 0");
    }

    @Test
    void testCalculateTotalSampledVolume_ZeroDuration() {
        double volume = calculationService.calculateTotalSampledVolume(0.74367, 0);
        assertEquals(0, volume, "Volume should be 0 if duration is 0");
    }

    @Test
    void testAdjustForEnvironmentalFactors_NormalConditions() {
        double adjustedVolume = calculationService.adjustForEnvironmentalFactors(1.1155, 25.4, 999);
        assertEquals(1.1032, adjustedVolume, 0.01, "Adjusted volume should be calculated correctly");
    }

    @Test
    void testAdjustForEnvironmentalFactors_ZeroVolume() {
        double adjustedVolume = calculationService.adjustForEnvironmentalFactors(0, 25.4, 999);
        assertEquals(0, adjustedVolume, "Adjusted volume should be 0 if initial volume is 0");
    }

    @Test
    void testAdjustForEnvironmentalFactors_ZeroPressure() {
        double adjustedVolume = calculationService.adjustForEnvironmentalFactors(1.1155, 25.4, 0);
        assertEquals(0, adjustedVolume, "Adjusted volume should be 0 if pressure is 0 to avoid division error");
    }

}
