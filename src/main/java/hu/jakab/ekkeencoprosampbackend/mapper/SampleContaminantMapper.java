package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.sampleAnalyticalResult.SampleAnalyticalResultRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sampleContaminant.SampleContaminantListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sampleContaminant.SampleContaminantResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.SampleContaminant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SampleMapper.class, ContaminantMapper.class, MeasurementUnitMapper.class, AnalyticalLabReportMapper.class})
public interface SampleContaminantMapper {

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SampleContaminant toEntity(SampleAnalyticalResultRequestDTO dto);

    @Mapping(target = "sample", source = "sample")
    @Mapping(target = "contaminant", source = "contaminant")
    SampleContaminantResponseDTO toResponseDTO(SampleContaminant entity);

    @Mapping(target = "sample", source = "sample")
    @Mapping(target = "contaminant", source = "contaminant")
    SampleContaminantListItemDTO toCreatedDTO(SampleContaminant entity);
}
