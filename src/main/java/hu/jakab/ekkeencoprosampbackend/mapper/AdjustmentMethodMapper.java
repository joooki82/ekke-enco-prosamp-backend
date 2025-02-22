package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.AdjustmentMethodRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.AdjustmentMethodResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.AdjustmentMethod;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdjustmentMethodMapper {
    AdjustmentMethodMapper INSTANCE = Mappers.getMapper(AdjustmentMethodMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AdjustmentMethod toEntity(AdjustmentMethodRequestDTO dto);

    AdjustmentMethodResponseDTO toResponseDTO(AdjustmentMethod entity);
}
