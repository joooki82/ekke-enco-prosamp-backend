package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.AdjustmentMethodMapper;
import hu.jakab.ekkeencoprosampbackend.model.AdjustmentMethod;
import hu.jakab.ekkeencoprosampbackend.repository.AdjustmentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdjustmentMethodService {
    private final AdjustmentMethodRepository repository;

    private final AdjustmentMethodMapper mapper;

    @Autowired
    public AdjustmentMethodService(AdjustmentMethodRepository repository, AdjustmentMethodMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<AdjustmentMethodResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<AdjustmentMethodResponseDTO> getById(Long id) {
        return repository.findById(id).map(mapper::toResponseDTO);
    }

    public AdjustmentMethodCreatedDTO save(AdjustmentMethodRequestDTO dto) {
        AdjustmentMethod adjustmentMethod = mapper.toEntity(dto);
        AdjustmentMethod savedMethod = repository.save(adjustmentMethod);
        return mapper.toCreatedDTO(savedMethod);
    }

    public Optional<AdjustmentMethodResponseDTO> update(Long id, AdjustmentMethodRequestDTO dto) {
        return repository.findById(id).map(existing -> {
            // Prevent overwriting fields with null values
            if (dto.getCode() != null) existing.setCode(dto.getCode());
            if (dto.getDescription() != null) existing.setDescription(dto.getDescription());

            try {
                AdjustmentMethod updatedMethod = repository.save(existing);
                return mapper.toResponseDTO(updatedMethod);
            } catch (DataIntegrityViolationException e) {
                throw new RuntimeException("Update failed: Duplicate 'code' value");
            }
        }).or(() -> {
            throw new ResourceNotFoundException("Adjustment method with ID " + id + " not found");
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
