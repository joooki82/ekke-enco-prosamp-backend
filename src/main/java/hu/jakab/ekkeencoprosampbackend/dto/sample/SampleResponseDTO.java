package hu.jakab.ekkeencoprosampbackend.dto.sample;

import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.measurementUnit.MeasurementUnitListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200.SamplingRecordDatM200ListItemDTO;
import hu.jakab.ekkeencoprosampbackend.model.MeasurementUnit;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleResponseDTO {
    
    private Long id;
    private SamplingRecordDatM200ListItemDTO samplingRecord;
    private String sampleIdentifier;
    private String location;
    private String employeeName;
    private BigDecimal temperature;
    private BigDecimal humidity;
    private BigDecimal pressure;
    private BigDecimal sampleVolumeFlowRate;
    private MeasurementUnitListItemDTO sampleVolumeFlowRateUnit;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String sampleType;
    private String status;
    private String remarks;
    private Long samplingTypeId;
    private AdjustmentMethodListItemDTO adjustmentMethod;
    private BigDecimal samplingFlowRate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
