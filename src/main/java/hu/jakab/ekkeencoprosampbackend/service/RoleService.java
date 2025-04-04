package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.role.RoleCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.role.RoleRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.role.RoleResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.RoleMapper;
import hu.jakab.ekkeencoprosampbackend.model.Role;
import hu.jakab.ekkeencoprosampbackend.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository repository;
    private final RoleMapper mapper;

    @Autowired
    public RoleService(RoleRepository repository, RoleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<RoleResponseDTO> getAll() {
        logger.info("Fetching all roles");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public RoleResponseDTO getById(Long id) {
        logger.info("Fetching role by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Role with ID " + id + " not found"));
    }

    @Transactional
    public RoleCreatedDTO save(RoleRequestDTO dto) {
        logger.info("Creating a new role with name: {}", dto.getRoleName());
        Role role = mapper.toEntity(dto);
        try {
            Role savedRole = repository.save(role);
            return mapper.toCreatedDTO(savedRole);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error saving role: Duplicate 'role_name' detected");
            throw new DuplicateResourceException("Failed to create role: Duplicate 'role_name' detected");
        }
    }

    @Transactional
    public RoleResponseDTO update(Long id, RoleRequestDTO dto) {
        logger.info("Updating role with ID: {}", id);
        Role existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with ID " + id + " not found"));

        if (dto.getRoleName() != null) existing.setRoleName(dto.getRoleName());

        try {
            Role updatedRole = repository.save(existing);
            return mapper.toResponseDTO(updatedRole);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update role: Duplicate 'role_name' detected");
            throw new DuplicateResourceException("Update failed: Duplicate 'role_name' value");
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting role with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Cannot delete: Role with ID {} not found", id);
            throw new ResourceNotFoundException("Role with ID " + id + " not found");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted role with ID: {}", id);
    }

}
