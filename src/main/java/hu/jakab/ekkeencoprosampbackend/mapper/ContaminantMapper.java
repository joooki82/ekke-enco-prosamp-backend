package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.ContaminantRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.ContaminantResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Contaminant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContaminantMapper {
    ContaminantMapper INSTANCE = Mappers.getMapper(ContaminantMapper.class);

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Contaminant toEntity(ContaminantRequestDTO dto);

    ContaminantResponseDTO toResponseDTO(Contaminant entity);
}
