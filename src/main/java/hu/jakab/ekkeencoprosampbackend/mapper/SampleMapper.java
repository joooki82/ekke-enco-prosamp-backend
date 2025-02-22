package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.SampleRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.SampleResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Sample;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SampleMapper {
    SampleMapper INSTANCE = Mappers.getMapper(SampleMapper.class);

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "samplingRecord", ignore = true) // Handled manually in Service
    @Mapping(target = "sampleVolumeFlowRateUnit", ignore = true) // Handled manually in Service
    @Mapping(target = "samplingType", ignore = true) // Handled manually in Service
    @Mapping(target = "adjustmentMethod", ignore = true) // Handled manually in Service
    Sample toEntity(SampleRequestDTO dto);

    @Mapping(source = "samplingRecord.id", target = "samplingRecordId")
    @Mapping(source = "sampleVolumeFlowRateUnit.id", target = "sampleVolumeFlowRateUnitId")
    @Mapping(source = "samplingType.id", target = "samplingTypeId")
    @Mapping(source = "adjustmentMethod.id", target = "adjustmentMethodId")
    SampleResponseDTO toResponseDTO(Sample entity);
}
