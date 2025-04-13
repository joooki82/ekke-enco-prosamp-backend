package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.laboratory.LaboratoryCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.laboratory.LaboratoryListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.laboratory.LaboratoryRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.laboratory.LaboratoryResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Laboratory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SampleMapper.class, AnalyticalLabReportMapper.class})
public interface LaboratoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Laboratory toEntity(LaboratoryRequestDTO dto);

    LaboratoryResponseDTO toResponseDTO(Laboratory entity);

    LaboratoryCreatedDTO toCreatedDTO(Laboratory entity);

    LaboratoryListItemDTO toListItemDTO(Laboratory entity);

}
