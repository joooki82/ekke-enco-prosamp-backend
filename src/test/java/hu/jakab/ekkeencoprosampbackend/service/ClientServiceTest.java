package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.client.ClientCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.client.ClientRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.client.ClientResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.ClientMapper;
import hu.jakab.ekkeencoprosampbackend.model.Client;
import hu.jakab.ekkeencoprosampbackend.repository.ClientRepository;
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
class ClientServiceTest {

    @Mock
    private ClientRepository repository;

    @Mock
    private ClientMapper mapper;

    @InjectMocks
    private ClientService clientService;

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        var result = clientService.getAll();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        Client client = new Client();
        when(repository.findById(1L)).thenReturn(Optional.of(client));
        when(mapper.toResponseDTO(client)).thenReturn(new ClientResponseDTO());
        var result = clientService.getById(1L);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> clientService.getById(1L));
    }

    @Test
    void testSave() {
        ClientRequestDTO dto = new ClientRequestDTO();
        Client client = new Client();
        when(mapper.toEntity(dto)).thenReturn(client);
        when(repository.save(client)).thenReturn(client);
        when(mapper.toCreatedDTO(client)).thenReturn(new ClientCreatedDTO());
        var result = clientService.save(dto);
        assertNotNull(result);
        verify(repository, times(1)).save(client);
    }

    @Test
    void testSaveDuplicate() {
        ClientRequestDTO dto = new ClientRequestDTO();
        Client client = new Client();
        when(mapper.toEntity(dto)).thenReturn(client);
        when(repository.save(client)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> clientService.save(dto));
    }

    @Test
    void testUpdate() {
        ClientRequestDTO dto = new ClientRequestDTO();
        Client client = new Client();
        when(repository.findById(1L)).thenReturn(Optional.of(client));
        when(repository.save(client)).thenReturn(client);
        when(mapper.toResponseDTO(client)).thenReturn(new ClientResponseDTO());
        var result = clientService.update(1L, dto);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(client);
    }

    @Test
    void testUpdateNotFound() {
        ClientRequestDTO dto = new ClientRequestDTO();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> clientService.update(1L, dto));
    }

    @Test
    void testUpdateDuplicate() {
        ClientRequestDTO dto = new ClientRequestDTO();
        Client client = new Client();
        when(repository.findById(1L)).thenReturn(Optional.of(client));
        when(repository.save(client)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> clientService.update(1L, dto));
    }

    @Test
    void testDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        clientService.delete(1L);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> clientService.delete(1L));
    }
}
