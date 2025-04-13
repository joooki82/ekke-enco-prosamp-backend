package hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticalLabReportRequestDTO {

    @NotBlank(message = "A jelentésszám megadása kötelező.")
    @Size(max = 50, message = "A jelentésszám legfeljebb 50 karakter lehet.")
    private String reportNumber;

    @NotNull(message = "A kiadás dátumának megadása kötelező.")
    private LocalDate issueDate;

    @NotNull(message = "A laboratórium azonosítója kötelező.")
    private Long laboratoryId;
}
