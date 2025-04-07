package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport.AnalyticalLabReportCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport.AnalyticalLabReportRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport.AnalyticalLabReportResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.AnalyticalLabReportMapper;
import hu.jakab.ekkeencoprosampbackend.model.AnalyticalLabReport;
import hu.jakab.ekkeencoprosampbackend.repository.AnalyticalLabReportRepository;
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
class AnalyticalLabReportServiceTest {

    @Mock
    private AnalyticalLabReportRepository repository;

    @Mock
    private AnalyticalLabReportMapper mapper;

    @InjectMocks
    private AnalyticalLabReportService analyticalLabReportService;

    @Test
    void testGetAllReports() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        var result = analyticalLabReportService.getAll();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetReportById() {
        AnalyticalLabReport report = new AnalyticalLabReport();
        when(repository.findById(1L)).thenReturn(Optional.of(report));
        when(mapper.toResponseDTO(report)).thenReturn(new AnalyticalLabReportResponseDTO());
        var result = analyticalLabReportService.getById(1L);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetReportByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> analyticalLabReportService.getById(1L));
    }

    @Test
    void testSaveReport() {
        AnalyticalLabReportRequestDTO dto = new AnalyticalLabReportRequestDTO();
        AnalyticalLabReport report = new AnalyticalLabReport();
        when(mapper.toEntity(dto)).thenReturn(report);
        when(repository.save(report)).thenReturn(report);
        when(mapper.toCreatedDTO(report)).thenReturn(new AnalyticalLabReportCreatedDTO());
        var result = analyticalLabReportService.save(dto);
        assertNotNull(result);
        verify(repository, times(1)).save(report);
    }

    @Test
    void testSaveReportDuplicate() {
        AnalyticalLabReportRequestDTO dto = new AnalyticalLabReportRequestDTO();
        AnalyticalLabReport report = new AnalyticalLabReport();
        when(mapper.toEntity(dto)).thenReturn(report);
        when(repository.save(report)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> analyticalLabReportService.save(dto));
    }

    @Test
    void testUpdateReport() {
        AnalyticalLabReportRequestDTO dto = new AnalyticalLabReportRequestDTO();
        AnalyticalLabReport report = new AnalyticalLabReport();
        when(repository.findById(1L)).thenReturn(Optional.of(report));
        when(repository.save(report)).thenReturn(report);
        when(mapper.toResponseDTO(report)).thenReturn(new AnalyticalLabReportResponseDTO());
        var result = analyticalLabReportService.update(1L, dto);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(report);
    }

    @Test
    void testUpdateReportNotFound() {
        AnalyticalLabReportRequestDTO dto = new AnalyticalLabReportRequestDTO();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> analyticalLabReportService.update(1L, dto));
    }

    @Test
    void testUpdateReportDuplicate() {
        AnalyticalLabReportRequestDTO dto = new AnalyticalLabReportRequestDTO();
        AnalyticalLabReport report = new AnalyticalLabReport();
        when(repository.findById(1L)).thenReturn(Optional.of(report));
        when(repository.save(report)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> analyticalLabReportService.update(1L, dto));
    }

    @Test
    void testDeleteReport() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        analyticalLabReportService.delete(1L);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteReportNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> analyticalLabReportService.delete(1L));
    }
}
