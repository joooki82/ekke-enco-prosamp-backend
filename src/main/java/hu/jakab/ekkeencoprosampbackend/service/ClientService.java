package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.client.ClientCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.client.ClientRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.client.ClientResponseDTO;
import hu.jakab.ekkeencoprosampbackend.mapper.ClientMapper;
import hu.jakab.ekkeencoprosampbackend.model.Client;
import hu.jakab.ekkeencoprosampbackend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository repository;
    private final ClientMapper mapper;

    @Autowired
    public ClientService(ClientRepository repository, ClientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ClientResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<ClientResponseDTO> getById(Long id) {
        return repository.findById(id).map(mapper::toResponseDTO);
    }

    public ClientCreatedDTO save(ClientRequestDTO dto) {
        Client client = mapper.toEntity(dto);
        Client savedClient = repository.save(client);
        return mapper.toCreatedDTO(savedClient);
    }

    public Optional<ClientResponseDTO> update(Long id, ClientRequestDTO dto) {
        return repository.findById(id).map(existing -> {
            // Prevent overwriting with null values
            if (dto.getName() != null) existing.setName(dto.getName());
            if (dto.getContactPerson() != null) existing.setContactPerson(dto.getContactPerson());
            if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
            if (dto.getPhone() != null) existing.setPhone(dto.getPhone());
            if (dto.getAddress() != null) existing.setAddress(dto.getAddress());
            if (dto.getCountry() != null) existing.setCountry(dto.getCountry());
            if (dto.getCity() != null) existing.setCity(dto.getCity());
            if (dto.getPostalCode() != null) existing.setPostalCode(dto.getPostalCode());
            if (dto.getTaxNumber() != null) existing.setTaxNumber(dto.getTaxNumber());

            try {
                Client updatedClient = repository.save(existing);
                return mapper.toResponseDTO(updatedClient);
            } catch (DataIntegrityViolationException e) {
                throw new RuntimeException("Cannot update client: Duplicate name or tax number");
            }
        });
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
