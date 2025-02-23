package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.CompanyRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.CompanyResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Company toEntity(CompanyRequestDTO dto);

    CompanyResponseDTO toResponseDTO(Company entity);
}
