package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignEquipmentDTO {
    
    @NotNull(message = "Sampling record ID cannot be null")
    private Long samplingRecordId;

    @NotNull(message = "Equipment ID cannot be null")
    private Long equipmentId;
}
