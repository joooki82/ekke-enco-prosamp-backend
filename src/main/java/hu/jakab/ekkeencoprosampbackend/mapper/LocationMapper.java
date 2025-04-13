package hu.jakab.ekkeencoprosampbackend.mapper;


import hu.jakab.ekkeencoprosampbackend.dto.location.LocationCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.location.LocationRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.location.LocationResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EntityMapperHelper.class})
public interface LocationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "company", source = "companyId", qualifiedByName = "mapCompany")
    Location toEntity(LocationRequestDTO dto);

    LocationResponseDTO toResponseDTO(Location entity);

    LocationCreatedDTO toCreatedDTO(Location entity);
}
