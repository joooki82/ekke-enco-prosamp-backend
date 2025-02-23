package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.request.ContaminantRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.ContaminantResponseDTO;
import hu.jakab.ekkeencoprosampbackend.mapper.ContaminantMapper;
import hu.jakab.ekkeencoprosampbackend.model.Contaminant;
import hu.jakab.ekkeencoprosampbackend.model.ContaminantGroup;
import hu.jakab.ekkeencoprosampbackend.repository.ContaminantGroupRepository;
import hu.jakab.ekkeencoprosampbackend.repository.ContaminantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContaminantService {
    private final ContaminantRepository repository;
    private final ContaminantGroupRepository groupRepository;
    private final ContaminantMapper mapper;


    @Autowired
    public ContaminantService(ContaminantRepository repository, ContaminantGroupRepository groupRepository, ContaminantMapper mapper) {
        this.repository = repository;
        this.groupRepository = groupRepository;
        this.mapper = mapper;
    }

    public List<ContaminantResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<ContaminantResponseDTO> getById(Long id) {
        return repository.findById(id).map(mapper::toResponseDTO);
    }

    public ContaminantResponseDTO save(ContaminantRequestDTO dto) {
        ContaminantGroup contaminantGroup = groupRepository.findById(dto.getContaminantGroupId())
                .orElseThrow(() -> new RuntimeException("Contaminant group not found"));

        Contaminant contaminant = mapper.toEntity(dto);
        contaminant.setContaminantGroup(contaminantGroup);

        Contaminant savedContaminant = repository.save(contaminant);
        return mapper.toResponseDTO(savedContaminant);
    }

    public Optional<ContaminantResponseDTO> update(Long id, ContaminantRequestDTO dto) {
        return repository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setDescription(dto.getDescription());

            ContaminantGroup contaminantGroup = groupRepository.findById(dto.getContaminantGroupId())
                    .orElseThrow(() -> new RuntimeException("Contaminant group not found"));
            existing.setContaminantGroup(contaminantGroup);

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
