package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.ContaminantGroupRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.ContaminantGroupResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.ContaminantGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContaminantGroupMapper {
    ContaminantGroupMapper INSTANCE = Mappers.getMapper(ContaminantGroupMapper.class);

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ContaminantGroup toEntity(ContaminantGroupRequestDTO dto);

    ContaminantGroupResponseDTO toResponseDTO(ContaminantGroup entity);
}
