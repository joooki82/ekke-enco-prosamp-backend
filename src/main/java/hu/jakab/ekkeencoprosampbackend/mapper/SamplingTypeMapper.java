package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectResponseDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingType.SamplingTypeCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingType.SamplingTypeRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingType.SamplingTypeResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Project;
import hu.jakab.ekkeencoprosampbackend.model.SamplingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SamplingTypeMapper {

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SamplingType toEntity(SamplingTypeRequestDTO dto);

    SamplingTypeResponseDTO toResponseDTO(SamplingType entity);

    SamplingTypeCreatedDTO toCreatedDTO(SamplingType entity);

}
