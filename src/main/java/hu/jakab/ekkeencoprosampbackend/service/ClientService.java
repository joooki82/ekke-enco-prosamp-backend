package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.client.ClientCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.client.ClientRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.client.ClientResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.ClientMapper;
import hu.jakab.ekkeencoprosampbackend.model.Client;
import hu.jakab.ekkeencoprosampbackend.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository repository;
    private final ClientMapper mapper;

    @Autowired
    public ClientService(ClientRepository repository, ClientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ClientResponseDTO> getAll() {
        logger.info("Fetching all clients");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public ClientResponseDTO getById(Long id) {
        logger.info("Fetching client by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Client with ID " + id + " not found"));
    }

    @Transactional
    public ClientCreatedDTO save(ClientRequestDTO dto) {
        logger.info("Creating a new client with name: {} and tax number: {}", dto.getName(), dto.getTaxNumber());
        Client client = mapper.toEntity(dto);
        try {
            Client savedClient = repository.save(client);
            return mapper.toCreatedDTO(savedClient);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to save client with name '{}' and tax number '{}': Constraint violation - {}", dto.getName(), dto.getTaxNumber(), e.getMessage(), e);
            throw new DuplicateResourceException("A client with the same name or tax number already exists.");
        }
    }

    @Transactional
    public ClientResponseDTO update(Long id, ClientRequestDTO dto) {
        logger.info("Updating client (ID: {}) with new details", id);
        Client existing = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Update failed: Client with ID {} not found", id);
                    return new ResourceNotFoundException("Client with ID " + id + " not found.");
                });

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
            logger.error("Failed to update client with ID {} and tax number '{}': Constraint violation - {}", id, dto.getTaxNumber(), e.getMessage(), e);
            throw new DuplicateResourceException("Update failed: A client with the same name or tax number already exists.");
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting client with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Delete failed: Client with ID {} not found", id);
            throw new ResourceNotFoundException("Client with ID " + id + " not found.");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted client with ID: {}", id);
    }
}
