package hu.jakab.ekkeencoprosampbackend.mapper;


import hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200.SamplingRecordDatM200RequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200.SamplingRecordDatM200ResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.SamplingRecordDatM200;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SamplingRecordDatM200Mapper {
    SamplingRecordDatM200Mapper INSTANCE = Mappers.getMapper(SamplingRecordDatM200Mapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "project", ignore = true) // Handled manually in Service
    SamplingRecordDatM200 toEntity(SamplingRecordDatM200RequestDTO dto);

    @Mapping(source = "project.id", target = "projectId")
    SamplingRecordDatM200ResponseDTO toResponseDTO(SamplingRecordDatM200 entity);
}
