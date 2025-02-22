package hu.jakab.ekkeencoprosampbackend.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleAnalyticalResultResponseDTO {
    private Long id;
    private Long sampleId;
    private Long contaminantId;
    private BigDecimal resultMain;
    private BigDecimal resultControl;
    private BigDecimal resultMainControl;
    private Long measurementUnitId;
    private BigDecimal detectionLimit;
    private BigDecimal measurementUncertainty;
    private String analysisMethod;
    private Long labReportId;
    private LocalDateTime analysisDate;
    private BigDecimal calculatedConcentration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
