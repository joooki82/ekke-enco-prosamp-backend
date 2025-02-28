package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.CompanyMapper;
import hu.jakab.ekkeencoprosampbackend.model.Company;
import hu.jakab.ekkeencoprosampbackend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository repository;
    private final CompanyMapper mapper;

    @Autowired
    public CompanyService(CompanyRepository repository, CompanyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CompanyResponseDTO> getAll() {
        
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
      }

    public Optional<CompanyResponseDTO> getById(Long id) {

        return repository.findById(id)
                .map(mapper::toResponseDto);
    }

    public CompanyCreatedDTO save(CompanyRequestDTO dto) {
        Company company = mapper.toEntity(dto);
        Company savedCompany = repository.save(company);
        return mapper.toCreatedDto(savedCompany);
    }

    public Optional<CompanyResponseDTO> update(Long id, CompanyRequestDTO dto) {
        return repository.findById(id).map(existingCompany -> {
            existingCompany = mapper.toEntity(dto);
            existingCompany.setUpdatedAt(LocalDateTime.now());
            Company updatedCompany = repository.save(existingCompany);
            return mapper.toResponseDto(updatedCompany);
        });
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Company with ID " + id + " not found.");
        }
        repository.deleteById(id);
        return true;
    }
}
