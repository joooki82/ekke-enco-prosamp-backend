package hu.jakab.ekkeencoprosampbackend.mapper;


import hu.jakab.ekkeencoprosampbackend.dto.measurementUnit.MeasurementUnitBaseUnitDTO;
import hu.jakab.ekkeencoprosampbackend.dto.measurementUnit.MeasurementUnitCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.measurementUnit.MeasurementUnitRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.measurementUnit.MeasurementUnitResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.MeasurementUnit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EntityMapperHelper.class})
public interface MeasurementUnitMapper {

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "baseUnit", source = "baseUnitId", qualifiedByName = "mapMeasurementUnit")
    MeasurementUnit toEntity(MeasurementUnitRequestDTO dto);

    @Mapping(target = "baseUnit", source = "baseUnit") // Explicit mapping
    MeasurementUnitResponseDTO toResponseDTO(MeasurementUnit entity);

    MeasurementUnitCreatedDTO toCreatedDTO(MeasurementUnit entity);

    MeasurementUnitBaseUnitDTO toBaseUnitDTO(MeasurementUnit entity);
}
