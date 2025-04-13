package hu.jakab.ekkeencoprosampbackend.service.utils;

import hu.jakab.ekkeencoprosampbackend.model.Sample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CalculationEngineTest {

    private CalculationEngine calculationEngine;

    @BeforeEach
    void setUp() {
        calculationEngine = new CalculationEngine();
    }

    @Test
    void testCalculateSamplingDuration_withValidTimestamps_shouldReturnCorrectDurationInMinutes() {
        Sample sample = new Sample();
        sample.setStartTime(LocalDateTime.of(2024, 4, 1, 10, 0));
        sample.setEndTime(LocalDateTime.of(2024, 4, 1, 11, 30)); // 90 minutes

        BigDecimal duration = calculationEngine.calculateSamplingDuration(sample);

        assertEquals(new BigDecimal("90"), duration);
    }

    @Test
    void testCalculateSamplingDuration_withZeroDuration_shouldReturnZero() {
        Sample sample = new Sample();
        sample.setStartTime(LocalDateTime.of(2024, 4, 1, 10, 0));
        sample.setEndTime(LocalDateTime.of(2024, 4, 1, 10, 0)); // 0 minutes

        BigDecimal duration = calculationEngine.calculateSamplingDuration(sample);

        assertEquals(BigDecimal.ZERO, duration);
    }

    @Test
    void testCalculateSamplingDuration_withNegativeDuration_shouldReturnNegativeValue() {
        Sample sample = new Sample();
        sample.setStartTime(LocalDateTime.of(2024, 4, 1, 11, 0));
        sample.setEndTime(LocalDateTime.of(2024, 4, 1, 10, 0)); // -60 minutes

        BigDecimal duration = calculationEngine.calculateSamplingDuration(sample);

        assertEquals(new BigDecimal("-60"), duration);
    }

    @Test
    void testAdjustedVolumeFlowByEnvironmentalFactors_withStandardConditions_shouldReturnOriginalFlowRate() {
        Sample sample = new Sample();
        sample.setPressure(new BigDecimal("1013.25"));
        sample.setTemperature(new BigDecimal("25.0"));
        sample.setSampleVolumeFlowRate(new BigDecimal("100.0"));

        BigDecimal adjustedFlow = calculationEngine.adjustedVolumeFlowByEnvironmentalFactors(sample);

        assertEquals(new BigDecimal("100.0000"), adjustedFlow);
    }

    @Test
    void testAdjustedVolumeFlowByEnvironmentalFactors_withLowerPressure_shouldIncreaseFlowRate() {
        Sample sample = new Sample();
        sample.setPressure(new BigDecimal("900.0"));
        sample.setTemperature(new BigDecimal("25.0"));
        sample.setSampleVolumeFlowRate(new BigDecimal("100.0"));

        BigDecimal adjustedFlow = calculationEngine.adjustedVolumeFlowByEnvironmentalFactors(sample);
        assertTrue(adjustedFlow.compareTo(new BigDecimal("100.0000")) < 0);
    }

    @Test
    void testAdjustedVolumeFlowByEnvironmentalFactors_withHigherTemperature_shouldDecreaseFlowRate() {
        Sample sample = new Sample();
        sample.setPressure(new BigDecimal("1013.25"));
        sample.setTemperature(new BigDecimal("35.0"));
        sample.setSampleVolumeFlowRate(new BigDecimal("100.0"));

        BigDecimal adjustedFlow = calculationEngine.adjustedVolumeFlowByEnvironmentalFactors(sample);
        assertTrue(adjustedFlow.compareTo(new BigDecimal("100.0000")) < 0);
    }

    @Test
    void testCalculateAdjustedTotalSampledVolume_withNormalValues_shouldCalculateCorrectly() {
        Sample sample = new Sample();
        sample.setStartTime(LocalDateTime.of(2024, 4, 1, 10, 0));
        sample.setEndTime(LocalDateTime.of(2024, 4, 1, 11, 0));
        sample.setPressure(new BigDecimal("1013.25"));
        sample.setTemperature(new BigDecimal("25.0"));
        sample.setSampleVolumeFlowRate(new BigDecimal("5.0"));

        BigDecimal result = calculationEngine.calculateAdjustedTotalSampledVolume(sample);
        assertEquals(new BigDecimal("300.0000"), result); // 5 * 60
    }

    @Test
    void testCalculateAdjustedTotalSampledVolume_shouldThrowExceptionWhenFlowRateIsNull() {
        Sample sample = new Sample();
        sample.setStartTime(LocalDateTime.of(2024, 4, 1, 10, 0));
        sample.setEndTime(LocalDateTime.of(2024, 4, 1, 11, 0));
        sample.setPressure(new BigDecimal("1013.25"));
        sample.setTemperature(new BigDecimal("25.0"));
        sample.setSampleVolumeFlowRate(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                calculationEngine.calculateAdjustedTotalSampledVolume(sample));

        assertEquals("Sample volume flow rate cannot be null", exception.getMessage());
    }

    @Test
    void testCalculateAdjustedTotalSampledVolume_withNonStandardConditions() {
        Sample sample = new Sample();
        sample.setStartTime(LocalDateTime.of(2024, 4, 1, 10, 0));
        sample.setEndTime(LocalDateTime.of(2024, 4, 1, 10, 30)); // 30 min
        sample.setPressure(new BigDecimal("950.0"));
        sample.setTemperature(new BigDecimal("15.0"));
        sample.setSampleVolumeFlowRate(new BigDecimal("4.0"));

        BigDecimal result = calculationEngine.calculateAdjustedTotalSampledVolume(sample);

        assertNotNull(result);
        assertTrue(result.compareTo(BigDecimal.ZERO) > 0);
    }


}
