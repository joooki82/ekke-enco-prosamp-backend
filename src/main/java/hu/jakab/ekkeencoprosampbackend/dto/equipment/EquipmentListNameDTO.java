package hu.jakab.ekkeencoprosampbackend.dto.equipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentListNameDTO {
    private Long id;
    private String name;
    private String identifier;
}
