package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.model.*;
import hu.jakab.ekkeencoprosampbackend.repository.*;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityMapperHelper {

    private final SamplingRecordDatM200Repository samplingRecordRepository;
    private final MeasurementUnitRepository measurementUnitRepository;
    private final SamplingTypeRepository samplingTypeRepository;
    private final AdjustmentMethodRepository adjustmentMethodRepository;
    private final CompanyRepository companyRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final ContaminantGroupRepository contaminantGroupRepository;
    private final SampleContaminantRepository sampleContaminantRepository;
    private final AnalyticalLabReportRepository analyticalLabReportRepository;
    private final SampleRepository sampleRepository;
    private final ContaminantRepository contaminantRepository;

    @Autowired
    public EntityMapperHelper(SamplingRecordDatM200Repository samplingRecordRepository,
                              MeasurementUnitRepository measurementUnitRepository,
                              SamplingTypeRepository samplingTypeRepository,
                              AdjustmentMethodRepository adjustmentMethodRepository,
                              CompanyRepository companyRepository,
                              LaboratoryRepository laboratoryRepository,
                              ContaminantGroupRepository contaminantGroupRepository,
                              SampleContaminantRepository sampleContaminantRepository,
                              AnalyticalLabReportRepository analyticalLabReportRepository,
                              SampleRepository sampleRepository,
                              ContaminantRepository contaminantRepository) {
        this.samplingRecordRepository = samplingRecordRepository;
        this.measurementUnitRepository = measurementUnitRepository;
        this.samplingTypeRepository = samplingTypeRepository;
        this.adjustmentMethodRepository = adjustmentMethodRepository;
        this.companyRepository = companyRepository;
        this.laboratoryRepository = laboratoryRepository;
        this.contaminantGroupRepository = contaminantGroupRepository;
        this.sampleContaminantRepository = sampleContaminantRepository;
        this.analyticalLabReportRepository = analyticalLabReportRepository;
        this.sampleRepository = sampleRepository;
        this.contaminantRepository = contaminantRepository;
    }

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
        return id == null ? null : sampleContaminantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SampleContaminant not found with ID: " + id));
    }

    @Named("mapAnalyticalLabReport")
    public AnalyticalLabReport mapAnalyticalLabReport(Long id) {
        return id == null ? null : analyticalLabReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SampleContaminant not found with ID: " + id));
    }

    @Named("mapSample")
    public Sample mapSample(Long id) {
        return id == null ? null : sampleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sample not found with ID: " + id));
    }

    @Named("mapContaminant")
    public Contaminant mapContaminant(Long id) {
        return id == null ? null : contaminantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contaminant not found with ID: " + id));
    }
}
