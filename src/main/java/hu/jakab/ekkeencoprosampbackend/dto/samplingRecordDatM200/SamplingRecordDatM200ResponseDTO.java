package hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200;

import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.equipment.EquipmentListNameDTO;
import hu.jakab.ekkeencoprosampbackend.dto.location.LocationListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SamplingRecordDatM200ResponseDTO {

    private Long id;

    private LocalDateTime samplingDate;

    private UserDTO conductedBy;

    private CompanyListItemDTO company;

    private LocationListItemDTO siteLocation;

    private String testedPlant;

    private String technology;

    private Integer shiftCountAndDuration;

    private Integer workersPerShift;

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

    private ProjectListItemDTO project;

    private String status;

    private String remarks;

    private List<EquipmentListNameDTO> samplingRecordEquipments;

    private List<SampleListItemDTO> samples;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
