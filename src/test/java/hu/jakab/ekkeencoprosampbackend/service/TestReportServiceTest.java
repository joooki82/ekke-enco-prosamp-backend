package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.TestReportMapper;
import hu.jakab.ekkeencoprosampbackend.model.*;
import hu.jakab.ekkeencoprosampbackend.repository.*;
import hu.jakab.ekkeencoprosampbackend.service.laTex.LaTeXReportService;
import hu.jakab.ekkeencoprosampbackend.service.laTex.LatexContentBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestReportServiceTest {

    @Mock
    private TestReportRepository repository;

    @Mock
    private TestReportMapper mapper;

    @InjectMocks
    private TestReportService testReportService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private SamplingRecordDatM200Repository samplingRecordRepository;

    @Mock
    private TestReportStandardRepository testReportStandardRepository;

    @Mock
    private TestReportSamplerRepository testReportSamplerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StandardRepository standardRepository;

    @Mock
    private LaTeXReportService latexReportService;

    @Mock
    private LatexContentBuilder latexContentBuilder;


    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        var result = testReportService.getAll();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        TestReport testReport = new TestReport();
        when(repository.findById(1L)).thenReturn(Optional.of(testReport));
        when(mapper.toResponseDTO(testReport)).thenReturn(new TestReportResponseDTO());
        var result = testReportService.getById(1L);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> testReportService.getById(1L));
    }

    @Test
    void testSave() {
        TestReportRequestDTO dto = new TestReportRequestDTO();
        TestReport testReport = new TestReport();
        when(mapper.toEntity(dto)).thenReturn(testReport);
        when(repository.save(testReport)).thenReturn(testReport);
        when(mapper.toCreatedDTO(testReport)).thenReturn(new TestReportCreatedDTO());
        var result = testReportService.save(dto);
        assertNotNull(result);
        verify(repository, times(1)).save(testReport);
    }

    @Test
    void testSaveDuplicate() {
        TestReportRequestDTO dto = new TestReportRequestDTO();
        TestReport testReport = new TestReport();
        when(mapper.toEntity(dto)).thenReturn(testReport);
        when(repository.save(testReport)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> testReportService.save(dto));
    }

    @Test
    void testUpdate() {
        TestReportRequestDTO dto = new TestReportRequestDTO();
        TestReport testReport = new TestReport();
        when(repository.findById(1L)).thenReturn(Optional.of(testReport));
        when(repository.save(testReport)).thenReturn(testReport);
        when(mapper.toResponseDTO(testReport)).thenReturn(new TestReportResponseDTO());
        var result = testReportService.update(1L, dto);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(testReport);
    }

    @Test
    void testUpdateNotFound() {
        TestReportRequestDTO dto = new TestReportRequestDTO();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> testReportService.update(1L, dto));
    }

    @Test
    void testUpdateDuplicate() {
        TestReportRequestDTO dto = new TestReportRequestDTO();
        TestReport testReport = new TestReport();
        when(repository.findById(1L)).thenReturn(Optional.of(testReport));
        when(repository.save(testReport)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> testReportService.update(1L, dto));
    }

    @Test
    void testDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        testReportService.delete(1L);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> testReportService.delete(1L));
    }


    @Test
    void testGenerateReport() throws IOException, InterruptedException {
        // Arrange
        Long testReportId = 1L;
        TestReport testReport = new TestReport();
        testReport.setId(testReportId);
        testReport.setReportNumber("TR-001");
        testReport.setTitle("Test Report Title");
        testReport.setIssueDate(LocalDate.now());
        testReport.setProject(new Project());
        testReport.setLocation(new Location());
        testReport.setSamplingRecord(new SamplingRecordDatM200());
        testReport.getSamplingRecord().setCompany(new Company());
        testReport.setTestReportStandards(new ArrayList<>());
        testReport.setTestReportSamplers(new ArrayList<>());

        when(repository.findById(testReportId)).thenReturn(Optional.of(testReport));
        when(latexReportService.generatePdfReport(anyMap())).thenReturn(new byte[0]);

        // Act
        byte[] result = testReportService.generateReport(testReportId);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).findById(testReportId);
        verify(latexReportService, times(1)).generatePdfReport(anyMap());
    }
}
