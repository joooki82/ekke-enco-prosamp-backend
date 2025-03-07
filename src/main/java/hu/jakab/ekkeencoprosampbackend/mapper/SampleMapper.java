package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Sample;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {EntityMapperHelper.class})
public interface SampleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)

    @Mapping(target = "samplingRecord", source = "samplingRecordId", qualifiedByName = "mapSamplingRecord")
    @Mapping(target = "sampleVolumeFlowRateUnit", source = "sampleVolumeFlowRateUnitId", qualifiedByName = "mapMeasurementUnit")
    @Mapping(target = "samplingType", source = "samplingTypeId", qualifiedByName = "mapSamplingType")
    @Mapping(target = "adjustmentMethod", source = "adjustmentMethodId", qualifiedByName = "mapAdjustmentMethod")
    Sample toEntity(SampleRequestDTO dto);

    @Mapping(target = "samplingRecord", source = "samplingRecord")
    @Mapping(target = "sampleVolumeFlowRateUnit", source = "sampleVolumeFlowRateUnit")
    @Mapping(target = "samplingType", source = "samplingType")
    @Mapping(target = "adjustmentMethod", source = "adjustmentMethod")
    SampleResponseDTO toResponseDTO(Sample entity);

    SampleCreatedDTO toCreatedDTO(Sample entity);

    @Mapping(target = "samplingRecordId", source = "samplingRecord.id")
    SampleListItemDTO toListItemDTO(Sample entity);

}
