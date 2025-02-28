package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Sample;
import hu.jakab.ekkeencoprosampbackend.model.SamplingRecordDatM200;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {EntityMapperHelper.class, SamplingRecordDatM200.class})
public interface SampleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)

    @Mapping(target = "samplingRecord", source = "samplingRecordId", qualifiedByName = "mapSamplingRecord")
    @Mapping(target = "sampleVolumeFlowRateUnit", source = "sampleVolumeFlowRateUnitId", qualifiedByName = "mapMeasurementUnit")
    @Mapping(target = "samplingType", source = "samplingTypeId", qualifiedByName = "mapSamplingType")
    @Mapping(target = "adjustmentMethod", source = "adjustmentMethodId", qualifiedByName = "mapAdjustmentMethod")
    Sample toEntity(SampleRequestDTO dto);

    @Mapping(target = "samplingRecordId", source = "samplingRecord.id")
    SampleResponseDTO toResponseDTO(Sample entity);

    SampleCreatedDTO toCreatedDTO(Sample entity);

}
