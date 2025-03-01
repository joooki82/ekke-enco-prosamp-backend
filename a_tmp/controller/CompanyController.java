package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.controller.base.BaseController;
import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/companies")
public class CompanyController extends BaseController<CompanyRequestDTO, CompanyResponseDTO, CompanyCreatedDTO, Long> {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    private final CompanyService service;

    @Autowired
    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @Override
    public List<CompanyResponseDTO> getAllEntities() {
        logger.info("Fetching all companies");
        return service.getAll();
    }

    @Override
    public Optional<CompanyResponseDTO> getEntityById(Long id) {
        logger.info("Fetching company by ID: {}", id);
        return service.getById(id);
    }

    @Override
    public CompanyCreatedDTO createEntity(CompanyRequestDTO dto) {
        logger.info("Creating a new company: {}", dto.getName());
        return service.save(dto);
    }

    @Override
    public Optional<CompanyResponseDTO> updateEntity(Long id, CompanyRequestDTO dto) {
        logger.info("Updating company with ID: {}", id);
        return service.update(id, dto);
    }

    @Override
    public boolean deleteEntity(Long id) {
        logger.info("Deleting company with ID: {}", id);
        return service.delete(id);
    }
}
