package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.CompanyMapper;
import hu.jakab.ekkeencoprosampbackend.model.Company;
import hu.jakab.ekkeencoprosampbackend.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository repository;

    @Mock
    private CompanyMapper mapper;

    @InjectMocks
    private CompanyService companyService;

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        var result = companyService.getAll();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        Company company = new Company();
        when(repository.findById(1L)).thenReturn(Optional.of(company));
        when(mapper.toResponseDTO(company)).thenReturn(new CompanyResponseDTO());
        var result = companyService.getById(1L);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> companyService.getById(1L));
    }

    @Test
    void testSave() {
        CompanyRequestDTO dto = new CompanyRequestDTO();
        Company company = new Company();
        when(mapper.toEntity(dto)).thenReturn(company);
        when(repository.save(company)).thenReturn(company);
        when(mapper.toCreatedDTO(company)).thenReturn(new CompanyCreatedDTO());
        var result = companyService.save(dto);
        assertNotNull(result);
        verify(repository, times(1)).save(company);
    }

    @Test
    void testSaveDuplicate() {
        CompanyRequestDTO dto = new CompanyRequestDTO();
        Company company = new Company();
        when(mapper.toEntity(dto)).thenReturn(company);
        when(repository.save(company)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> companyService.save(dto));
    }

    @Test
    void testUpdate() {
        CompanyRequestDTO dto = new CompanyRequestDTO();
        Company company = new Company();
        when(repository.findById(1L)).thenReturn(Optional.of(company));
        when(repository.save(company)).thenReturn(company);
        when(mapper.toResponseDTO(company)).thenReturn(new CompanyResponseDTO());
        var result = companyService.update(1L, dto);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(company);
    }

    @Test
    void testUpdateNotFound() {
        CompanyRequestDTO dto = new CompanyRequestDTO();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> companyService.update(1L, dto));
    }

    @Test
    void testUpdateDuplicate() {
        CompanyRequestDTO dto = new CompanyRequestDTO();
        Company company = new Company();
        when(repository.findById(1L)).thenReturn(Optional.of(company));
        when(repository.save(company)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> companyService.update(1L, dto));
    }

    @Test
    void testDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        companyService.delete(1L);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> companyService.delete(1L));
    }
}
