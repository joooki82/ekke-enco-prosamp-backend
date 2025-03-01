package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.AdjustmentMethod;
import hu.jakab.ekkeencoprosampbackend.model.SamplingRecordDatM200;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {SampleMapper.class})
public interface AdjustmentMethodMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AdjustmentMethod toEntity(AdjustmentMethodRequestDTO dto);

    @Mapping(target = "samples", source = "samples")
    AdjustmentMethodResponseDTO toResponseDTO(AdjustmentMethod entity);

    AdjustmentMethodCreatedDTO toCreatedDTO(AdjustmentMethod entity);

}
