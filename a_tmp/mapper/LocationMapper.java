package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.LocationRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.LocationResponseDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.CompanyBasicInfoDTO;
import hu.jakab.ekkeencoprosampbackend.model.Location;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    @Mapping(source = "companyId", target = "company.id")
    Location toEntity(LocationRequestDTO dto);

    @Mapping(target = "company", ignore = true) // Prevents recursion by ignoring company
    LocationResponseDTO toResponseDto(Location entity);

    List<LocationResponseDTO> toResponseDtoList(List<Location> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(LocationRequestDTO dto, @MappingTarget Location entity);
}
