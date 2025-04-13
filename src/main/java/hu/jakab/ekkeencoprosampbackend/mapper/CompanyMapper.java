package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Company toEntity(CompanyRequestDTO dto);

    CompanyResponseDTO toResponseDTO(Company entity);

    CompanyCreatedDTO toCreatedDTO(Company entity);
}
