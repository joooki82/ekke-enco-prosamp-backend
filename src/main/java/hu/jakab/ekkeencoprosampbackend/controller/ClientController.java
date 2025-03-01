package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.client.ClientCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.client.ClientRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.client.ClientResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.service.ClientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController  {


    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        logger.info("Fetching all clients");
        List<ClientResponseDTO> clients = service.getAll();
        return clients.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        logger.info("Fetching client by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ClientCreatedDTO> createClient(@RequestBody @Valid ClientRequestDTO dto) {
        logger.info("Creating a new client with name: {}", dto.getName());
        ClientCreatedDTO createdClient = service.save(dto);
        return ResponseEntity.status(201).body(createdClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id, @RequestBody @Valid ClientRequestDTO dto) {
        logger.info("Updating client (ID: {}) with new details", id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        logger.info("Deleting client with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
