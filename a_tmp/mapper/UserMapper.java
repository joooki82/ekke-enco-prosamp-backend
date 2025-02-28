package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.UserRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.UserResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "testReportSamplers", ignore = true) // Avoid relationships in DTOs
    User toEntity(UserRequestDTO dto);

    UserResponseDTO toResponseDTO(User entity);
}
