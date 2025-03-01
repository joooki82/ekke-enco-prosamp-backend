package hu.jakab.ekkeencoprosampbackend.dto.testreport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestReporCreatedDTO {
    private Long id;
    private String reportNumber;
    private String title;

    private String aimOfTest;
    private String technology;
    private String samplingConditionsDates;
    private String determinationOfPollutantConcentration;

    private Long projectId;
    private Long clientId;
    private Long locationId;
    private Long samplingRecordId;

}
