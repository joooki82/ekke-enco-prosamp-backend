package hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SamplingRecordDatM200RequestDTO {

    @NotNull(message = "Sampling date is required")
    private LocalDateTime samplingDate;

    @NotNull(message = "Conducted by (User ID) is required")
    private UUID conductedById;

    @NotNull(message = "Company ID is required")
    private Long companyId;

    @NotNull(message = "Site location ID is required")
    private Long siteLocationId;

    private String testedPlant;

    private String technology;

    private Integer shiftCountAndDuration;

    private Integer workersPerShift;

    @NotNull(message = "Exposure time is required")
    private Long exposureTime;

    private BigDecimal temperature;

    private BigDecimal humidity;

    private BigDecimal windSpeed;

    private BigDecimal pressure1;

    private BigDecimal pressure2;

    private String otherEnvironmentalConditions;

    private String airFlowConditions;

    private String operationMode;

    private String operationBreak;

    private String localAirExtraction;

    private String serialNumbersOfSamples;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    @NotNull(message = "Status is required")
    private String status;

    private String remarks;

    private List<Long> equipmentIds;
}
