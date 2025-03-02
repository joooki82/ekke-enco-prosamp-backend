package hu.jakab.ekkeencoprosampbackend.dto.equipment;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentRequestDTO {

    @NotBlank(message = "Equipment name cannot be empty")
    private String name;
    @NotBlank(message = "Identifier cannot be empty")
    private String identifier;
    private String description;
    private String manufacturer;
    private String type;
    private String serialNumber;
    private String measuringRange;
    private String resolution;
    private String accuracy;
    private LocalDate calibrationDate;
    private LocalDate nextCalibrationDate;

}
