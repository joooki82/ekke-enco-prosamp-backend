package hu.jakab.ekkeencoprosampbackend.dto.sample;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleCreatedDTO {
    
    private Long id;
    private Long samplingRecordId;
    private String sampleIdentifier;
    private String location;
    private String employeeName;
    private BigDecimal temperature;
    private BigDecimal humidity;
    private BigDecimal pressure;
    private BigDecimal sampleVolumeFlowRate;
    private Long sampleVolumeFlowRateUnitId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String sampleType;
    private String status;
    private String remarks;
    private Long samplingTypeId;
    private Long adjustmentMethodId;
}
