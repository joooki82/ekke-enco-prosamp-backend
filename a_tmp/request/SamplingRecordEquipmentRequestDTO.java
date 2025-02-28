package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SamplingRecordEquipmentRequestDTO {

    @NotNull(message = "Sampling record ID cannot be null")
    private Long samplingRecordId;

    @NotNull(message = "Equipment ID cannot be null")
    private Long equipmentId;

    private String remarks; // Additional field in join table
}
