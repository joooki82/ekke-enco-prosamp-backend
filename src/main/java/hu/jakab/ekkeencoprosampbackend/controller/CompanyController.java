package hu.jakab.ekkeencoprosampbackend.controller;


import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.CompanyService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {


    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponseDTO>> getAllCompanys() {
        logger.info("Fetching all company");
        List<CompanyResponseDTO> companies = service.getAll();
        return ResponseEntity.ok(companies != null ? companies : Collections.emptyList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> getCompanyById(@PathVariable Long id) {
        logger.info("Fetching company by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<CompanyCreatedDTO> createCompany(@RequestBody @Valid CompanyRequestDTO dto) {
        logger.info("Creating a new company with name: {}", dto.getName());
        CompanyCreatedDTO createdCompany = service.save(dto);
        return ResponseEntity.status(201).body(createdCompany);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> updateCompany(@PathVariable Long id, @RequestBody @Valid CompanyRequestDTO dto) {
        logger.info("Updating company (ID: {}) with new details", id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        logger.info("Deleting company with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
