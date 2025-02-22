package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.LocationRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.LocationResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "company", ignore = true) // Handled manually in Service
    Location toEntity(LocationRequestDTO dto);

    @Mapping(source = "company.id", target = "companyId")
    LocationResponseDTO toResponseDTO(Location entity);
}
