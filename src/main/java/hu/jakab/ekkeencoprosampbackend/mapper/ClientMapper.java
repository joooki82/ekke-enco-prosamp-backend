package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.client.ClientCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.client.ClientRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.client.ClientResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Client toEntity(ClientRequestDTO dto);

    ClientResponseDTO toResponseDTO(Client entity);

    ClientCreatedDTO toCreatedDTO(Client entity);
}
