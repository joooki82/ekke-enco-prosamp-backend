package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.SampleAnalyticalResultRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.SampleAnalyticalResultResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.SampleAnalyticalResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SampleAnalyticalResultMapper {
    SampleAnalyticalResultMapper INSTANCE = Mappers.getMapper(SampleAnalyticalResultMapper.class);

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "sample", ignore = true)
    @Mapping(target = "contaminant", ignore = true)
    @Mapping(target = "measurementUnit", ignore = true)
    @Mapping(target = "labReport", ignore = true)
    SampleAnalyticalResult toEntity(SampleAnalyticalResultRequestDTO dto);

    @Mapping(source = "sample.id", target = "sampleId")
    @Mapping(source = "contaminant.id", target = "contaminantId")
    @Mapping(source = "measurementUnit.id", target = "measurementUnitId")
    @Mapping(source = "labReport.id", target = "labReportId")
    SampleAnalyticalResultResponseDTO toResponseDTO(SampleAnalyticalResult entity);
}
