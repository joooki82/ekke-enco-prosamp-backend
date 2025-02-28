package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.StandardRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.StandardResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Standard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StandardMapper {
    StandardMapper INSTANCE = Mappers.getMapper(StandardMapper.class);

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Standard toEntity(StandardRequestDTO dto);

    StandardResponseDTO toResponseDTO(Standard entity);
}
