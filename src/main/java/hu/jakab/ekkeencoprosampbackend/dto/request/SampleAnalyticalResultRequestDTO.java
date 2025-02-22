package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleAnalyticalResultRequestDTO {
    @NotNull(message = "Sample ID is required")
    private Long sampleId;

    @NotNull(message = "Contaminant ID is required")
    private Long contaminantId;

    @NotNull(message = "Result main value is required")
    private BigDecimal resultMain;

    private BigDecimal resultControl;
    private BigDecimal resultMainControl;

    @NotNull(message = "Measurement unit ID is required")
    private Long measurementUnitId;

    private BigDecimal detectionLimit;
    private BigDecimal measurementUncertainty;
    private String analysisMethod;

    @NotNull(message = "Lab report ID is required")
    private Long labReportId;

    private LocalDateTime analysisDate;
    private BigDecimal calculatedConcentration;
}
