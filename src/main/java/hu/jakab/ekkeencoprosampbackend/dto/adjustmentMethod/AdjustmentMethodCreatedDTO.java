package hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdjustmentMethodCreatedDTO {
    private Long id;
    private String code;
    private String description;
}
