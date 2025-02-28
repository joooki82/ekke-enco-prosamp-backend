package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.request.ClientRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.ClientResponseDTO;
import hu.jakab.ekkeencoprosampbackend.mapper.ClientMapper;
import hu.jakab.ekkeencoprosampbackend.model.Client;
import hu.jakab.ekkeencoprosampbackend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ClientResponseDTO save(ClientRequestDTO dto) {
        Client client = mapper.toEntity(dto);
        Client savedClient = repository.save(client);
        return mapper.toResponseDTO(savedClient);
    }

    public Optional<ClientResponseDTO> update(Long id, ClientRequestDTO dto) {
        return repository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setContactPerson(dto.getContactPerson());
            existing.setEmail(dto.getEmail());
            existing.setPhone(dto.getPhone());
            existing.setAddress(dto.getAddress());
            existing.setCountry(dto.getCountry());
            existing.setCity(dto.getCity());
            existing.setPostalCode(dto.getPostalCode());
            existing.setTaxNumber(dto.getTaxNumber());
            repository.save(existing);
            return mapper.toResponseDTO(existing);
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
