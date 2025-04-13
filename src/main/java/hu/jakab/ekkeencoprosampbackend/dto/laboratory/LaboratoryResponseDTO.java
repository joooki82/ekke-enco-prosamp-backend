package hu.jakab.ekkeencoprosampbackend.dto.laboratory;

import hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport.AnalyticalLabReportListItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaboratoryResponseDTO {
    private Long id;
    private String name;
    private String accreditation;
    private String contactEmail;
    private String phone;
    private String address;
    private String website;
    private List<AnalyticalLabReportListItemDTO> reports = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
