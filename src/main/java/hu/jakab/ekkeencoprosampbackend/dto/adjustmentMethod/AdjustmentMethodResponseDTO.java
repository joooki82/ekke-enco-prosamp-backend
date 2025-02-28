package hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod;

import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleListItemDTO;
import hu.jakab.ekkeencoprosampbackend.model.Sample;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdjustmentMethodResponseDTO {
    private Long id;
    private String code;
    private String description;
    private List<SampleListItemDTO> samples = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
