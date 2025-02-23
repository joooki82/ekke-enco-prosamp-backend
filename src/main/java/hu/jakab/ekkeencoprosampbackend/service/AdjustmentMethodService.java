package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.request.AdjustmentMethodRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.AdjustmentMethodResponseDTO;
import hu.jakab.ekkeencoprosampbackend.mapper.AdjustmentMethodMapper;
import hu.jakab.ekkeencoprosampbackend.model.AdjustmentMethod;
import hu.jakab.ekkeencoprosampbackend.repository.AdjustmentMethodRepository;
import org.mapstruct.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
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

    public AdjustmentMethodResponseDTO save(AdjustmentMethodRequestDTO dto) {
        AdjustmentMethod adjustmentMethod = mapper.toEntity(dto);
        AdjustmentMethod savedMethod = repository.save(adjustmentMethod);
        return mapper.toResponseDTO(savedMethod);
    }

    public Optional<AdjustmentMethodResponseDTO> update(Long id, AdjustmentMethodRequestDTO dto) {
        return repository.findById(id).map(existing -> {
            existing.setCode(dto.getCode());
            existing.setDescription(dto.getDescription());
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
