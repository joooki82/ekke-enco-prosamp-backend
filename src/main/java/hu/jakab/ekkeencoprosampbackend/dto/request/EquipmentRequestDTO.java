package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentRequestDTO {
    @NotBlank(message = "Identifier cannot be empty")
    private String identifier;

    @NotBlank(message = "Equipment name cannot be empty")
    private String name;

    private String type;
    private String description;
    private String serialNumber;
    private String manufacturer;
    private LocalDate calibrationDate;
    private LocalDate nextCalibrationDate;
}
