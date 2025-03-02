package hu.jakab.ekkeencoprosampbackend.mapper;


import hu.jakab.ekkeencoprosampbackend.dto.equipment.EquipmentCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.equipment.EquipmentRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.equipment.EquipmentResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Equipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EquipmentMapper {

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Equipment toEntity(EquipmentRequestDTO dto);

    EquipmentResponseDTO toResponseDTO(Equipment entity);

    EquipmentCreatedDTO toCreatedDTO(Equipment entity);
}
