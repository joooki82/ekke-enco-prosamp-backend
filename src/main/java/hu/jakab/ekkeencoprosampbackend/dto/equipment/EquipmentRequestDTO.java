package hu.jakab.ekkeencoprosampbackend.dto.equipment;

import jakarta.validation.constraints.NotBlank;
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
public class EquipmentRequestDTO {

    @NotBlank(message = "Az eszköz neve nem lehet üres.")
    @Size(max = 255, message = "Az eszköz neve legfeljebb 255 karakter lehet.")
    private String name;

    @NotBlank(message = "Az azonosító megadása kötelező.")
    @Size(max = 255, message = "Az azonosító legfeljebb 255 karakter lehet.")
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
