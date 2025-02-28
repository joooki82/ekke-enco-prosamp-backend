package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodResponseDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleResponseDTO;
import hu.jakab.ekkeencoprosampbackend.mapper.AdjustmentMethodMapper;
import hu.jakab.ekkeencoprosampbackend.mapper.SampleMapper;
import hu.jakab.ekkeencoprosampbackend.model.AdjustmentMethod;
import hu.jakab.ekkeencoprosampbackend.model.Sample;
import hu.jakab.ekkeencoprosampbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SampleService {

    private final SampleRepository repository;

    private final SampleMapper mapper;

    private final SamplingRecordDatM200Repository samplingRecordRepository;
    private final MeasurementUnitRepository measurementUnitRepository;
    private final SamplingTypeRepository samplingTypeRepository;
    private final AdjustmentMethodRepository adjustmentMethodRepository;

    @Autowired
    public SampleService(SampleRepository repository,
                         SampleMapper mapper,
                         SamplingRecordDatM200Repository samplingRecordRepository,
                         MeasurementUnitRepository measurementUnitRepository,
                         SamplingTypeRepository samplingTypeRepository,
                         AdjustmentMethodRepository adjustmentMethodRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.samplingRecordRepository = samplingRecordRepository;
        this.measurementUnitRepository = measurementUnitRepository;
        this.samplingTypeRepository = samplingTypeRepository;
        this.adjustmentMethodRepository = adjustmentMethodRepository;
    }

    public List<SampleResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<SampleResponseDTO> getById(Long id) {
        return repository.findById(id).map(mapper::toResponseDTO);
    }

    public SampleCreatedDTO save(SampleRequestDTO dto) {
        Sample Sample = mapper.toEntity(dto);
        Sample savedMethod = repository.save(Sample);
        return mapper.toCreatedDTO(savedMethod);
    }

    public Optional<SampleResponseDTO> update(Long id, SampleRequestDTO dto) {
        return repository.findById(id).map(existing -> {

            // Update simple fields
            existing.setSampleIdentifier(dto.getSampleIdentifier());
            existing.setLocation(dto.getLocation());
            existing.setEmployeeName(dto.getEmployeeName());
            existing.setTemperature(dto.getTemperature());
            existing.setHumidity(dto.getHumidity());
            existing.setPressure(dto.getPressure());
            existing.setSampleVolumeFlowRate(dto.getSampleVolumeFlowRate());
            existing.setStartTime(dto.getStartTime());
            existing.setEndTime(dto.getEndTime());
            existing.setSampleType(dto.getSampleType());
            existing.setStatus(dto.getStatus());
            existing.setRemarks(dto.getRemarks());
            existing.setSamplingFlowRate(dto.getSamplingFlowRate());

            existing.setSamplingRecord(samplingRecordRepository.findById(dto.getSamplingRecordId())
                    .orElseThrow(() -> new RuntimeException("Sampling Record not found with ID: " + dto.getSamplingRecordId())));

            existing.setSampleVolumeFlowRateUnit(measurementUnitRepository.findById(dto.getSampleVolumeFlowRateUnitId())
                    .orElseThrow(() -> new RuntimeException("Measurement Unit not found with ID: " + dto.getSampleVolumeFlowRateUnitId())));

            if (dto.getSamplingTypeId() != null) {
                existing.setSamplingType(samplingTypeRepository.findById(dto.getSamplingTypeId())
                        .orElseThrow(() -> new RuntimeException("Sampling Type not found with ID: " + dto.getSamplingTypeId())));
            } else {
                existing.setSamplingType(null); // Ensure it is cleared if null
            }

            if (dto.getAdjustmentMethodId() != null) {
                existing.setAdjustmentMethod(adjustmentMethodRepository.findById(dto.getAdjustmentMethodId())
                        .orElseThrow(() -> new RuntimeException("Adjustment Method not found with ID: " + dto.getAdjustmentMethodId())));
            } else {
                existing.setAdjustmentMethod(null);
            }
            repository.save(existing);
            return mapper.toResponseDTO(existing);
        });
    }



    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }


}
