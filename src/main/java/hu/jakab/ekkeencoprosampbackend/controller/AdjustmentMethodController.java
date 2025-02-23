package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.controller.base.BaseController;
import hu.jakab.ekkeencoprosampbackend.dto.request.AdjustmentMethodRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.AdjustmentMethodResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.GlobalExceptionHandler;
import hu.jakab.ekkeencoprosampbackend.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/adjustment-methods")
public class AdjustmentMethodController extends BaseController<AdjustmentMethodRequestDTO, AdjustmentMethodResponseDTO, Long> {

    private static final Logger logger = LoggerFactory.getLogger(AdjustmentMethodController.class);

    private final AdjustmentMethodService service;

    @Autowired
    public AdjustmentMethodController(AdjustmentMethodService service) {
        this.service = service;
    }

    @Override
    public List<AdjustmentMethodResponseDTO> getAllEntities() {
        logger.info("Fetching all adjustment methods");
        return service.getAll();
    }

    @Override
    public Optional<AdjustmentMethodResponseDTO> getEntityById(Long id) {
        logger.info("Fetching adjustment method by ID: {}", id);
        return service.getById(id);
    }

    @Override
    public AdjustmentMethodResponseDTO createEntity(AdjustmentMethodRequestDTO dto) {
        logger.info("Creating a new adjustment method");
        return service.save(dto);
    }

    @Override
    public Optional<AdjustmentMethodResponseDTO> updateEntity(Long id, AdjustmentMethodRequestDTO dto) {
        logger.info("Updating adjustment method with ID: {}", id);
        return service.update(id, dto);
    }

    @Override
    public boolean deleteEntity(Long id) {
        logger.info("Deleting adjustment method with ID: {}", id);
        return service.delete(id);
    }
}


