package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.SamplingRecordEquipmentRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.SamplingRecordEquipmentResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.SamplingRecordEquipment;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SamplingRecordEquipmentMapper {
    SamplingRecordEquipmentMapper INSTANCE = Mappers.getMapper(SamplingRecordEquipmentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "samplingRecord", ignore = true) // Handled manually in service
    @Mapping(target = "equipment", ignore = true) // Handled manually in service
    SamplingRecordEquipment toEntity(SamplingRecordEquipmentRequestDTO dto);

    @Mapping(source = "samplingRecord.id", target = "samplingRecordId")
    @Mapping(source = "equipment.id", target = "equipmentId")
    @Mapping(source = "equipment.name", target = "equipmentName") // Fetch name for convenience
    SamplingRecordEquipmentResponseDTO toResponseDTO(SamplingRecordEquipment entity);
}
