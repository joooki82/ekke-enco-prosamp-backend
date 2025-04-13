package hu.jakab.ekkeencoprosampbackend.dto.testreport;

import hu.jakab.ekkeencoprosampbackend.dto.location.LocationListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200.SamplingRecordDatM200ListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.standard.StandardListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestReportResponseDTO {

    private Long id;
    private String reportNumber;
    private String title;
    private UserDTO approvedBy;
    private UserDTO preparedBy;
    private UserDTO checkedBy;
    private String aimOfTest;
    private ProjectListItemDTO project;
    private List<StandardListItemDTO> testReportStandards;
    private List<UserDTO> testReportSamplers;
    private LocationListItemDTO location;
    private SamplingRecordDatM200ListItemDTO samplingRecord;
    private String technology;
    private String samplingConditionsDates;
    private String determinationOfPollutantConcentration;
    private LocalDate issueDate;
    private String reportStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
