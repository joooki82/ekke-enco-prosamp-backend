package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.LaboratoryRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.LaboratoryResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Laboratory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LaboratoryMapper {
    LaboratoryMapper INSTANCE = Mappers.getMapper(LaboratoryMapper.class);

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Laboratory toEntity(LaboratoryRequestDTO dto);

    LaboratoryResponseDTO toResponseDTO(Laboratory entity);
}
