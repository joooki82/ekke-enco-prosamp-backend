package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.controller.SampleAnalyticalResultController;
import hu.jakab.ekkeencoprosampbackend.dto.sampleContaminant.SampleContaminantCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sampleContaminant.SampleContaminantRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sampleContaminant.SampleWithContaminantsDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sampleContaminant.SampleWithSampleContaminantsDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.SampleContaminantMapper;
import hu.jakab.ekkeencoprosampbackend.model.Contaminant;
import hu.jakab.ekkeencoprosampbackend.model.Sample;
import hu.jakab.ekkeencoprosampbackend.model.SampleContaminant;
import hu.jakab.ekkeencoprosampbackend.repository.ContaminantRepository;
import hu.jakab.ekkeencoprosampbackend.repository.SampleContaminantRepository;
import hu.jakab.ekkeencoprosampbackend.repository.SampleRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleContaminantService {

    private static final Logger logger = LoggerFactory.getLogger(SampleAnalyticalResultController.class);
    private final SampleRepository sampleRepository;
    private final ContaminantRepository contaminantRepository;
    private final SampleContaminantRepository sampleContaminantRepository;
    private final SampleContaminantMapper mapper;

    public SampleContaminantService(SampleRepository sampleRepository, ContaminantRepository contaminantRepository, SampleContaminantRepository sampleContaminantRepository, SampleContaminantMapper mapper) {
        this.sampleRepository = sampleRepository;
        this.contaminantRepository = contaminantRepository;
        this.sampleContaminantRepository = sampleContaminantRepository;
        this.mapper = mapper;
    }
    @Transactional
    public SampleContaminantCreatedDTO linkSampleToContaminant(SampleContaminantRequestDTO requestDTO) {
        logger.info("Linking Sample ID '{}' to Contaminant ID '{}'", requestDTO.getSampleId(), requestDTO.getContaminantId());

        Sample sample = sampleRepository.findById(requestDTO.getSampleId())
                .orElseThrow(() -> new ResourceNotFoundException("Sample with ID " + requestDTO.getSampleId() + " not found."));

        Contaminant contaminant = contaminantRepository.findById(requestDTO.getContaminantId())
                .orElseThrow(() -> new ResourceNotFoundException("Contaminant with ID " + requestDTO.getContaminantId() + " not found."));

        sampleContaminantRepository.findBySampleAndContaminant(sample, contaminant)
                .ifPresent(existing -> {
                    logger.warn("Duplicate link attempt: Sample ID {} is already linked to Contaminant ID {}", sample.getId(), contaminant.getId());
                    throw new DuplicateResourceException("This sample is already linked to the given contaminant.");
                });

        SampleContaminant newLink = mapper.toEntity(requestDTO);
        SampleContaminant saved = sampleContaminantRepository.save(newLink);

        logger.info("Successfully linked Sample ID {} to Contaminant ID {}", sample.getId(), contaminant.getId());
        return mapper.toCreatedDTO(saved);
    }

    @Transactional
    public void unlinkSampleFromContaminant(SampleContaminantRequestDTO requestDTO) {
        logger.info("Unlinking Sample ID '{}' from Contaminant ID '{}'", requestDTO.getSampleId(), requestDTO.getContaminantId());

        Sample sample = sampleRepository.findById(requestDTO.getSampleId())
                .orElseThrow(() -> new ResourceNotFoundException("Sample with ID " + requestDTO.getSampleId() + " not found."));

        Contaminant contaminant = contaminantRepository.findById(requestDTO.getContaminantId())
                .orElseThrow(() -> new ResourceNotFoundException("Contaminant with ID " + requestDTO.getContaminantId() + " not found."));

        sampleContaminantRepository.findBySampleAndContaminant(sample, contaminant)
                .ifPresentOrElse(
                        sampleContaminant -> {
                            sampleContaminantRepository.delete(sampleContaminant);
                            logger.info("Successfully unlinked Sample ID {} from Contaminant ID {}", sample.getId(), contaminant.getId());
                        },
                        () -> logger.warn("No link found to remove between Sample ID {} and Contaminant ID {}", sample.getId(), contaminant.getId())
                );
    }

    public SampleWithContaminantsDTO getContaminantsBySample(Long sampleId) {
        logger.info("Fetching contaminants for Sample ID {}", sampleId);

        List<SampleContaminant> sampleContaminants = sampleContaminantRepository.findBySample(Sample.builder().id(sampleId).build());

        if (sampleContaminants.isEmpty()) {
            logger.warn("No contaminants found for Sample ID {}", sampleId);
            return null;
        }

        return mapper.mapToSampleWithContaminantsDTO(sampleContaminants);

    }

    public SampleWithSampleContaminantsDTO getSampleContaminantsBySample(Long sampleId) {
        logger.info("Fetching contaminants for Sample ID {}", sampleId);

        List<SampleContaminant> sampleContaminants = sampleContaminantRepository.findBySample(Sample.builder().id(sampleId).build());

        if (sampleContaminants.isEmpty()) {
            return null;
        }

        return mapper.mapToSampleWithSampleContaminantsDTO(sampleContaminants);

    }

}
