package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.request.ContaminantGroupRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.ContaminantGroupResponseDTO;
import hu.jakab.ekkeencoprosampbackend.mapper.ContaminantGroupMapper;
import hu.jakab.ekkeencoprosampbackend.model.ContaminantGroup;
import hu.jakab.ekkeencoprosampbackend.repository.ContaminantGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContaminantGroupService {

    private final ContaminantGroupRepository repository;
    private final ContaminantGroupMapper mapper;

    @Autowired
    public ContaminantGroupService(ContaminantGroupRepository repository, ContaminantGroupMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ContaminantGroupResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<ContaminantGroupResponseDTO> getById(Long id) {
        return repository.findById(id).map(mapper::toResponseDTO);
    }

    public ContaminantGroupResponseDTO save(ContaminantGroupRequestDTO dto) {
        ContaminantGroup contaminantGroup = mapper.toEntity(dto);
        ContaminantGroup savedGroup = repository.save(contaminantGroup);
        return mapper.toResponseDTO(savedGroup);
    }

    public Optional<ContaminantGroupResponseDTO> update(Long id, ContaminantGroupRequestDTO dto) {
        return repository.findById(id).map(existing -> {
            existing.setName(dto.getName());
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
