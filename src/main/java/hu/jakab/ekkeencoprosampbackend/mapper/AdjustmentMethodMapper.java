package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.AdjustmentMethod;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AdjustmentMethodMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AdjustmentMethod toEntity(AdjustmentMethodRequestDTO dto);

    AdjustmentMethodResponseDTO toResponseDTO(AdjustmentMethod entity);

    AdjustmentMethodCreatedDTO toCreatedDTO(AdjustmentMethod entity);

}
