package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.ProjectMapper;
import hu.jakab.ekkeencoprosampbackend.model.Client;
import hu.jakab.ekkeencoprosampbackend.model.Project;
import hu.jakab.ekkeencoprosampbackend.repository.ClientRepository;
import hu.jakab.ekkeencoprosampbackend.repository.ProjectRepository;
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
class ProjectServiceTest {

    @Mock
    private ProjectRepository repository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProjectMapper mapper;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        var result = projectService.getAll();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        Project project = new Project();
        when(repository.findById(1L)).thenReturn(Optional.of(project));
        when(mapper.toResponseDTO(project)).thenReturn(new ProjectResponseDTO());
        var result = projectService.getById(1L);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> projectService.getById(1L));
    }

    @Test
    void testSave() {
        ProjectRequestDTO dto = new ProjectRequestDTO();
        Project project = new Project();
        Client client = new Client();
        when(clientRepository.findById(dto.getClientId())).thenReturn(Optional.of(client));
        when(mapper.toEntity(dto)).thenReturn(project);
        when(repository.save(project)).thenReturn(project);
        when(mapper.toCreatedDTO(project)).thenReturn(new ProjectCreatedDTO());
        var result = projectService.save(dto);
        assertNotNull(result);
        verify(repository, times(1)).save(project);
    }

    @Test
    void testSaveDuplicate() {
        ProjectRequestDTO dto = new ProjectRequestDTO();
        Project project = new Project();
        Client client = new Client();
        when(clientRepository.findById(dto.getClientId())).thenReturn(Optional.of(client));
        when(mapper.toEntity(dto)).thenReturn(project);
        when(repository.save(project)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> projectService.save(dto));
    }

    @Test
    void testUpdate() {
        ProjectRequestDTO dto = new ProjectRequestDTO();
        Project project = new Project();
        Client client = new Client();
        when(repository.findById(1L)).thenReturn(Optional.of(project));
        when(clientRepository.findById(dto.getClientId())).thenReturn(Optional.of(client));
        when(repository.save(project)).thenReturn(project);
        when(mapper.toResponseDTO(project)).thenReturn(new ProjectResponseDTO());
        var result = projectService.update(1L, dto);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(project);
    }

    @Test
    void testUpdateNotFound() {
        ProjectRequestDTO dto = new ProjectRequestDTO();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> projectService.update(1L, dto));
    }

    @Test
    void testUpdateDuplicate() {
        ProjectRequestDTO dto = new ProjectRequestDTO();
        Project project = new Project();
        Client client = new Client();
        when(repository.findById(1L)).thenReturn(Optional.of(project));
        when(clientRepository.findById(dto.getClientId())).thenReturn(Optional.of(client));
        when(repository.save(project)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> projectService.update(1L, dto));
    }

    @Test
    void testDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        projectService.delete(1L);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> projectService.delete(1L));
    }
}
