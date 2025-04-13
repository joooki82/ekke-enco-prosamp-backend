package hu.jakab.ekkeencoprosampbackend.dto.testreport;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestReportRequestDTO {

    @NotBlank(message = "A jelentésszám megadása kötelező.")
    @Size(max = 50, message = "A jelentésszám legfeljebb 50 karakter lehet.")
    private String reportNumber;

    @NotBlank(message = "A cím megadása kötelező.")
    @Size(max = 255, message = "A cím legfeljebb 255 karakter lehet.")
    private String title;

    private UUID approvedBy;
    private UUID preparedBy;
    private UUID checkedBy;

    private String aimOfTest;

    @NotNull(message = "A projekt azonosító megadása kötelező.")
    private Long projectId;

    private List<Long> testReportStandardIds;
    private List<UUID> testReportSamplerIds;

    @NotNull(message = "A helyszín azonosító megadása kötelező.")
    private Long locationId;

    @NotNull(message = "A mintavételi jegyzőkönyv azonosító megadása kötelező.")
    private Long samplingRecordId;

    private String technology;
    private String samplingConditionsDates;
    private String determinationOfPollutantConcentration;

    @NotNull(message = "A kiállítás dátuma kötelező.")
    private LocalDate issueDate;

    @NotNull(message = "A jelentés státusz megadása kötelező.")
    private String reportStatus;

}
