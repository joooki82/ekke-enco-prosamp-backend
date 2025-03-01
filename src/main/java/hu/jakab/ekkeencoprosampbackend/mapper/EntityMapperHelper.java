package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.model.AdjustmentMethod;
import hu.jakab.ekkeencoprosampbackend.model.MeasurementUnit;
import hu.jakab.ekkeencoprosampbackend.model.SamplingRecordDatM200;
import hu.jakab.ekkeencoprosampbackend.model.SamplingType;
import hu.jakab.ekkeencoprosampbackend.repository.AdjustmentMethodRepository;
import hu.jakab.ekkeencoprosampbackend.repository.MeasurementUnitRepository;
import hu.jakab.ekkeencoprosampbackend.repository.SamplingRecordDatM200Repository;
import hu.jakab.ekkeencoprosampbackend.repository.SamplingTypeRepository;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityMapperHelper {

    @Autowired
    private SamplingRecordDatM200Repository samplingRecordRepository;

    @Autowired
    private MeasurementUnitRepository measurementUnitRepository;

    @Autowired
    private SamplingTypeRepository samplingTypeRepository;

    @Autowired
    private AdjustmentMethodRepository adjustmentMethodRepository;

    @Named("mapSamplingRecord")
    public SamplingRecordDatM200 mapSamplingRecord(Long id) {
        return id == null ? null : samplingRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sampling Record not found with ID: " + id));
    }

    @Named("mapMeasurementUnit")
    public MeasurementUnit mapMeasurementUnit(Long id) {
        return id == null ? null : measurementUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Measurement Unit not found with ID: " + id));
    }

    @Named("mapSamplingType")
    public SamplingType mapSamplingType(Long id) {
        return id == null ? null : samplingTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sampling Type not found with ID: " + id));
    }

    @Named("mapAdjustmentMethod")
    public AdjustmentMethod mapAdjustmentMethod(Long id) {
        return id == null ? null : adjustmentMethodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adjustment Method not found with ID: " + id));
    }
}
