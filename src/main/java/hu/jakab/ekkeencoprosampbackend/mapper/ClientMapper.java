package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.ClientRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.ClientResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Client toEntity(ClientRequestDTO dto);

    ClientResponseDTO toResponseDTO(Client entity);
}
