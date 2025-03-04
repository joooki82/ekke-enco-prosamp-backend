package hu.jakab.ekkeencoprosampbackend.dto.sampleAnalyticalResult;

import hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport.AnalyticalLabReportListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.measurementUnit.MeasurementUnitListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sampleContaminant.SampleContaminantListItemDTO;
import hu.jakab.ekkeencoprosampbackend.model.AnalyticalLabReport;
import hu.jakab.ekkeencoprosampbackend.model.MeasurementUnit;
import hu.jakab.ekkeencoprosampbackend.model.SampleContaminant;
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
    
    private MeasurementUnitListItemDTO measurementUnit;  // Full object
    
    private BigDecimal detectionLimit;
    
    private BigDecimal measurementUncertainty;
    
    private String analysisMethod;
    
    private AnalyticalLabReportListItemDTO labReport;  // Full object
    
    private LocalDateTime analysisDate;
    
    private BigDecimal calculatedConcentration;
    
}
