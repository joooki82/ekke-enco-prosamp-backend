package hu.jakab.ekkeencoprosampbackend.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequestDTO {

    @NotBlank(message = "A projekt azonosító megadása kötelező.")
    private String projectNumber;

    @NotNull(message = "Az ügyfél azonosítója kötelező.")
    private Long clientId;

    @NotBlank(message = "A projekt neve nem lehet üres.")
    private String projectName;

    @NotNull(message = "A projekt kezdési dátuma kötelező.")
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String description;
}
