package hu.jakab.ekkeencoprosampbackend.dto.testreport;

import hu.jakab.ekkeencoprosampbackend.dto.location.LocationListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestReportListItemDTO {

    private Long id;
    private String reportNumber;
    private String title;
    private UserDTO approvedBy;
    private LocationListItemDTO location;
    private LocalDate issueDate;
    private String reportStatus;


}
