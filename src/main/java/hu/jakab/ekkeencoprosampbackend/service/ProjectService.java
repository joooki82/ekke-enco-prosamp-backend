package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.ProjectMapper;
import hu.jakab.ekkeencoprosampbackend.model.Client;
import hu.jakab.ekkeencoprosampbackend.model.Project;
import hu.jakab.ekkeencoprosampbackend.repository.ClientRepository;
import hu.jakab.ekkeencoprosampbackend.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository repository;
    private final ClientRepository clientRepository;
    private final ProjectMapper mapper;

    @Autowired
    public ProjectService(ProjectRepository repository, ClientRepository clientRepository, ProjectMapper mapper) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.mapper = mapper;
    }

    public List<ProjectResponseDTO> getAll() {
        logger.info("Fetching all projects");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public ProjectResponseDTO getById(Long id) {
        logger.info("Fetching project by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Project with ID " + id + " not found"));
    }

    @Transactional
    public ProjectCreatedDTO save(ProjectRequestDTO dto) {
        logger.info("Creating new project with project number: '{}'", dto.getProjectNumber());

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> {
                    logger.warn("Save failed: Client with ID {} not found", dto.getClientId());
                    return new ResourceNotFoundException("Client with ID " + dto.getClientId() + " not found.");
                });

        Project project = mapper.toEntity(dto);
        project.setClient(client);

        try {
            Project savedProject = repository.save(project);
            return mapper.toCreatedDTO(savedProject);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to save project with project number '{}': Constraint violation - {}", dto.getProjectNumber(), e.getMessage(), e);
            throw new DuplicateResourceException("A project with number '" + dto.getProjectNumber() + "' already exists.");
        }
    }

    @Transactional
    public ProjectResponseDTO update(Long id, ProjectRequestDTO dto) {
        logger.info("Updating project with ID: {}", id);

        Project existing = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Update failed: Project with ID {} not found", id);
                    return new ResourceNotFoundException("Project with ID " + id + " not found.");
                });

        if (dto.getProjectNumber() != null) existing.setProjectNumber(dto.getProjectNumber());
        if (dto.getProjectName() != null) existing.setProjectName(dto.getProjectName());
        if (dto.getStartDate() != null) existing.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) existing.setEndDate(dto.getEndDate());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());

        if (dto.getClientId() != null) {
            Client client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> {
                        logger.warn("Update failed: Client with ID {} not found", dto.getClientId());
                        return new ResourceNotFoundException("Client with ID " + dto.getClientId() + " not found.");
                    });
            existing.setClient(client);
        }

        try {
            Project updatedProject = repository.save(existing);
            logger.info("Successfully updated project with ID: {}", id);
            return mapper.toResponseDTO(updatedProject);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update project with ID {}, number '{}': Constraint violation - {}", id, dto.getProjectNumber(), e.getMessage(), e);
            throw new DuplicateResourceException("Update failed: A project with number '" + dto.getProjectNumber() + "' already exists.");
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting project with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Cannot delete: Project with ID {} not found", id);
            throw new ResourceNotFoundException("Project with ID " + id + " not found");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted project with ID: {}", id);
    }
}
