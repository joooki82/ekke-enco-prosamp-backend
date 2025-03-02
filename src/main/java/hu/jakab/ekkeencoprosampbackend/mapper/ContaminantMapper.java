package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Contaminant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ContaminantGroupMapper.class, EntityMapperHelper.class})
public interface ContaminantMapper {

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "contaminantGroup", source = "contaminantGroupId", qualifiedByName = "mapContaminantGroup")
    Contaminant toEntity(ContaminantRequestDTO dto);

    ContaminantResponseDTO toResponseDTO(Contaminant entity);

    ContaminantCreatedDTO toCreatedDTO(Contaminant entity);

    ContaminantListItemDTO toListItemDTO(Contaminant entity);

}
