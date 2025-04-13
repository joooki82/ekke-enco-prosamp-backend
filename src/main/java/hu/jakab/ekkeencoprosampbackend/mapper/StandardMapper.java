package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.standard.StandardCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.standard.StandardListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.standard.StandardRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.standard.StandardResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Standard;
import hu.jakab.ekkeencoprosampbackend.model.StandardType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface StandardMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Standard toEntity(StandardRequestDTO dto);

    @Mapping(target = "standardType", source = "standardType")
    @Mapping(target = "standardTypeMagyar", source = "standardType", qualifiedByName = "toMagyarName")
    StandardResponseDTO toResponseDTO(Standard entity);

    StandardCreatedDTO toCreatedDTO(Standard entity);

    StandardListItemDTO toListItemDTO(Standard entity);

    @Named("toMagyarName")
    static String mapStandardTypeToMagyarName(StandardType type) {
        return type != null ? type.getMagyarName() : null;
    }

}
