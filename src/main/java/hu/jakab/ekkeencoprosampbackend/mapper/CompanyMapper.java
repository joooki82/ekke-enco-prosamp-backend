package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.CompanyRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.CompanyResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Company;
import hu.jakab.ekkeencoprosampbackend.model.Location;
import org.mapstruct.*;

import java.util.List;

import hu.jakab.ekkeencoprosampbackend.dto.response.LocationResponseDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = LocationMapper.class)
public interface CompanyMapper {

    Company toEntity(CompanyRequestDTO dto);

    @Mapping(source = "locations", target = "locations", qualifiedByName = "mapLocationsToDtoList")
    CompanyResponseDTO toResponseDto(Company entity);

    List<CompanyResponseDTO> toResponseDtoList(List<Company> entities);

    @Named("mapLocationsToDtoList")
    default List<LocationResponseDTO> mapLocationsToDtoList(List<Location> locations) {
        return locations != null ? locations.stream().map(LocationMapper.INSTANCE::toResponseDto).toList() : null;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CompanyRequestDTO dto, @MappingTarget Company entity);
}
