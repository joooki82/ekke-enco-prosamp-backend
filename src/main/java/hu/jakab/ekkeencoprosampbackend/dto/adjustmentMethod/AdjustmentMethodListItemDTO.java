package hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdjustmentMethodListItemDTO {
    private Long id;
    private String code;
    private String description;
}
