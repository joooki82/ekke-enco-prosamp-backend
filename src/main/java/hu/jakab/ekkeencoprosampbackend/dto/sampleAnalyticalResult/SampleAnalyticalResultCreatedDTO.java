package hu.jakab.ekkeencoprosampbackend.dto.sampleAnalyticalResult;

import hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport.AnalyticalLabReportListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.measurementUnit.MeasurementUnitListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sampleContaminant.SampleContaminantListItemDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleAnalyticalResultCreatedDTO {

    private Long id;

    private SampleContaminantListItemDTO sampleContaminant;  // Full object

    private BigDecimal resultMain;

    private BigDecimal resultControl;

    private BigDecimal resultMainControl;

    private MeasurementUnitListItemDTO resultMeasurementUnit;  // Full object

    private BigDecimal detectionLimit;

    private BigDecimal measurementUncertainty;

    private String analysisMethod;

    private AnalyticalLabReportListItemDTO labReport;  // Full object

    private LocalDateTime analysisDate;

    private BigDecimal calculatedConcentration;

    private MeasurementUnitListItemDTO calculatedConcentrationMeasurementUnit;

}
