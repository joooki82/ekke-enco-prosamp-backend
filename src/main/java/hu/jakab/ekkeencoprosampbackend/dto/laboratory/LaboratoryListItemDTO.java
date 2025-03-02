package hu.jakab.ekkeencoprosampbackend.dto.laboratory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaboratoryListItemDTO {
    private Long id;
    private String name;
    private String accreditation;
}
