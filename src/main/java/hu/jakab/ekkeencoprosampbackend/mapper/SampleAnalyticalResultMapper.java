package hu.jakab.ekkeencoprosampbackend.mapper;


import hu.jakab.ekkeencoprosampbackend.dto.sampleAnalyticalResult.SampleAnalyticalResultCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sampleAnalyticalResult.SampleAnalyticalResultRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sampleAnalyticalResult.SampleAnalyticalResultResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.SampleAnalyticalResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EntityMapperHelper.class, SampleMapper.class, ContaminantMapper.class, MeasurementUnitMapper.class, AnalyticalLabReportMapper.class, SampleContaminantMapper.class})
public interface SampleAnalyticalResultMapper {

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "resultMeasurementUnit", source = "resultMeasurementUnitId", qualifiedByName = "mapMeasurementUnit")
    @Mapping(target = "sampleContaminant", source = "sampleContaminantId", qualifiedByName = "mapSampleContaminant")
    @Mapping(target = "labReport", source = "labReportId", qualifiedByName = "mapAnalyticalLabReport")
    SampleAnalyticalResult toEntity(SampleAnalyticalResultRequestDTO dto);

    @Mapping(target = "sampleContaminant", source = "sampleContaminant")
    @Mapping(target = "resultMeasurementUnit", source = "resultMeasurementUnit")
    @Mapping(target = "labReport", source = "labReport")
    SampleAnalyticalResultResponseDTO toResponseDTO(SampleAnalyticalResult entity);

    SampleAnalyticalResultCreatedDTO toCreatedDTO(SampleAnalyticalResult entity);
}
