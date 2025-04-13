package hu.jakab.ekkeencoprosampbackend.dto.contaminantGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaminantGroupListItemDTO {
    private Long id;
    private String name;
    private String description;
}
