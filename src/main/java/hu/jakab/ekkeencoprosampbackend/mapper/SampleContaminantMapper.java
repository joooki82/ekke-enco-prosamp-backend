package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.SampleContaminantRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.SampleContaminantResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.SampleContaminant;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SampleContaminantMapper {
    SampleContaminantMapper INSTANCE = Mappers.getMapper(SampleContaminantMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sample", ignore = true) // Handled manually in service
    @Mapping(target = "contaminant", ignore = true) // Handled manually in service
    SampleContaminant toEntity(SampleContaminantRequestDTO dto);

    @Mapping(source = "sample.id", target = "sampleId")
    @Mapping(source = "contaminant.id", target = "contaminantId")
    @Mapping(source = "contaminant.name", target = "contaminantName")
    SampleContaminantResponseDTO toResponseDTO(SampleContaminant entity);
}
