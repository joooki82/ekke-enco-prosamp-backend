package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleRequestDTO {
    
    @NotNull(message = "Sampling record ID cannot be null")
    private Long samplingRecordId;

    @NotBlank(message = "Sample identifier cannot be empty")
    private String sampleIdentifier;

    private String location;
    private String employeeName;
    private BigDecimal temperature;
    private BigDecimal humidity;
    private BigDecimal pressure;
    private BigDecimal sampleVolumeFlowRate;

    @NotNull(message = "Measurement unit ID for flow rate cannot be null")
    private Long sampleVolumeFlowRateUnitId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @NotBlank(message = "Sample type cannot be empty")
    private String sampleType;

    @NotBlank(message = "Status cannot be empty")
    private String status;

    private String remarks;

    private Long samplingTypeId;
    private Long adjustmentMethodId;
    private BigDecimal samplingFlowRate;
}
