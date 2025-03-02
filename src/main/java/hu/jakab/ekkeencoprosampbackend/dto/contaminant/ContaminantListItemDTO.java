package hu.jakab.ekkeencoprosampbackend.dto.contaminant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaminantListItemDTO {
    private Long id;
    private String name;
    private String description;
}
