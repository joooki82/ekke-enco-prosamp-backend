package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.request.CompanyRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.CompanyResponseDTO;
import hu.jakab.ekkeencoprosampbackend.mapper.CompanyMapper;
import hu.jakab.ekkeencoprosampbackend.model.Company;
import hu.jakab.ekkeencoprosampbackend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<CompanyResponseDTO> getById(Long id) {
        return repository.findById(id).map(mapper::toResponseDTO);
    }

    public CompanyResponseDTO save(CompanyRequestDTO dto) {
        Company company = mapper.toEntity(dto);
        Company savedCompany = repository.save(company);
        return mapper.toResponseDTO(savedCompany);
    }

    public Optional<CompanyResponseDTO> update(Long id, CompanyRequestDTO dto) {
        return repository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setAddress(dto.getAddress());
            existing.setContactPerson(dto.getContactPerson());
            existing.setEmail(dto.getEmail());
            existing.setPhone(dto.getPhone());
            existing.setCountry(dto.getCountry());
            existing.setCity(dto.getCity());
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
