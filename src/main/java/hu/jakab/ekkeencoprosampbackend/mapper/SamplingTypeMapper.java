package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.SamplingTypeRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.SamplingTypeResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.SamplingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SamplingTypeMapper {
    SamplingTypeMapper INSTANCE = Mappers.getMapper(SamplingTypeMapper.class);

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SamplingType toEntity(SamplingTypeRequestDTO dto);

    SamplingTypeResponseDTO toResponseDTO(SamplingType entity);
}
