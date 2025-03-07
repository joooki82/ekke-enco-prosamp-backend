package hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200;

import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.equipment.EquipmentListNameDTO;
import hu.jakab.ekkeencoprosampbackend.dto.location.LocationListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectListItemDTO;
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
public class SamplingRecordDatM200ListItemDTO {

    private Long id;

    private LocalDateTime samplingDate;

    private UserDTO conductedBy;

    private CompanyListItemDTO company;

    private LocationListItemDTO siteLocation;

    private String testedPlant;

    private ProjectListItemDTO project;

}
