package hu.jakab.ekkeencoprosampbackend.dto.contaminantGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaminantGroupCreatedDTO {
    private Long id;
    private String name;
    private String description;
}
