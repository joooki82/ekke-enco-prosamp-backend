package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Company;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = LocationMapper.class)
public interface CompanyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Company toEntity(CompanyRequestDTO dto);

    @Mapping(target = "locations", source = "locations")
    CompanyResponseDTO toResponseDto(Company entity);

    CompanyCreatedDTO toCreatedDto(Company entity);

}
