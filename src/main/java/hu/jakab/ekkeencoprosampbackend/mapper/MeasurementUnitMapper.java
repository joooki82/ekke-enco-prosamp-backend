package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.MeasurementUnitRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.MeasurementUnitResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.MeasurementUnit;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MeasurementUnitMapper {
    MeasurementUnitMapper INSTANCE = Mappers.getMapper(MeasurementUnitMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "baseUnit", ignore = true) // Handled manually in Service
    MeasurementUnit toEntity(MeasurementUnitRequestDTO dto);

    MeasurementUnitResponseDTO toResponseDTO(MeasurementUnit entity);
}
