package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.model.*;
import hu.jakab.ekkeencoprosampbackend.repository.*;
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

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    @Autowired
    private ContaminantGroupRepository contaminantGroupRepository;

    @Autowired
    private SampleContaminantRepository sampleContaminantrepository;

    @Autowired
    private AnalyticalLabReportRepository analyticalLabReportrepository;



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

    @Named("mapCompany")
    public Company mapCompany(Long id) {
        return id == null ? null : companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));
    }

    @Named("mapLaboratory")
    public Laboratory mapLaboratory(Long id) {
        return id == null ? null : laboratoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laboratory not found with ID: " + id));
    }

    @Named("mapContaminantGroup")
    public ContaminantGroup mapContaminantGroup(Long id) {
        return id == null ? null : contaminantGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContaminantGroup not found with ID: " + id));
    }

    @Named("mapSampleContaminant")
    public SampleContaminant mapSampleContaminant(Long id) {
        return id == null ? null : sampleContaminantrepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SampleContaminant not found with ID: " + id));
    }

    @Named("mapAnalyticalLabReport")
    public AnalyticalLabReport mapAnalyticalLabReport(Long id) {
        return id == null ? null : analyticalLabReportrepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SampleContaminant not found with ID: " + id));
    }
}
