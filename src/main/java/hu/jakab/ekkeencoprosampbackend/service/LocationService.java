package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.request.LocationRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.LocationResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.LocationMapper;
import hu.jakab.ekkeencoprosampbackend.model.Company;
import hu.jakab.ekkeencoprosampbackend.model.Location;
import hu.jakab.ekkeencoprosampbackend.repository.CompanyRepository;
import hu.jakab.ekkeencoprosampbackend.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository repository;
    private final CompanyRepository companyRepository;
    private final LocationMapper mapper;

    @Autowired
    public LocationService(LocationRepository repository, CompanyRepository companyRepository, LocationMapper mapper) {
        this.repository = repository;
        this.companyRepository = companyRepository;
        this.mapper = mapper;
    }

    public List<LocationResponseDTO> getAll() {
        return mapper.toResponseDtoList(repository.findAll());
    }

    public Optional<LocationResponseDTO> getById(Long id) {
        return repository.findById(id).map(mapper::toResponseDto);
    }

    public LocationResponseDTO save(LocationRequestDTO dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company with ID " + dto.getCompanyId() + " not found."));

        Location location = mapper.toEntity(dto);
        location.setCompany(company);
        Location savedLocation = repository.save(location);

        return mapper.toResponseDto(savedLocation);
    }

    public Optional<LocationResponseDTO> update(Long id, LocationRequestDTO dto) {
        return repository.findById(id).map(existingLocation -> {
            mapper.updateEntityFromDto(dto, existingLocation);
            existingLocation.setUpdatedAt(java.time.LocalDateTime.now());
            Location updatedLocation = repository.save(existingLocation);
            return mapper.toResponseDto(updatedLocation);
        });
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Location with ID " + id + " not found.");
        }
        repository.deleteById(id);
        return true;
    }

}
