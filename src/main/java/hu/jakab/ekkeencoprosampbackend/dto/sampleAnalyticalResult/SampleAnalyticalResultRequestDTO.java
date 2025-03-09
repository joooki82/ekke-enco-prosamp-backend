package hu.jakab.ekkeencoprosampbackend.dto.sampleAnalyticalResult;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleAnalyticalResultRequestDTO {
    
    private Long sampleContaminantId;  // References SampleContaminant
    
    private BigDecimal resultMain;
    
    private BigDecimal resultControl;
    
    private BigDecimal resultMainControl;
    
    private Long resultMeasurementUnitId;  // References MeasurementUnit
    
    private BigDecimal detectionLimit;
    
    private BigDecimal measurementUncertainty;
    
    private String analysisMethod;
    
    private Long labReportId;  // References AnalyticalLabReport
    
    private LocalDateTime analysisDate;
    


}
