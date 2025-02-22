package hu.jakab.ekkeencoprosampbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "sampling_records_dat_m200")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SamplingRecordDatM200 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "sampling_date", nullable = false)
    private LocalDateTime samplingDate;

    @ManyToOne
    @JoinColumn(name = "conducted_by", nullable = false, foreignKey = @ForeignKey(name = "fk_sampling_conducted_by"))
    private User conductedBy;

    @ManyToOne
    @JoinColumn(name = "site_location_id", nullable = false, foreignKey = @ForeignKey(name = "fk_sampling_location"))
    private Location siteLocation;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false, foreignKey = @ForeignKey(name = "fk_sampling_company"))
    private Company company;

    @Column(name = "tested_plant", length = 255)
    private String testedPlant;

    @Column(name = "technology", length = 255)
    private String technology;

    @Column(name = "shift_count_and_duration")
    private Integer shiftCountAndDuration;

    @Column(name = "workers_per_shift")
    private Integer workersPerShift;

    @Column(name = "exposure_time", nullable = false)
    private Duration exposureTime;

    @Column(name = "temperature", precision = 5, scale = 2)
    private BigDecimal temperature;

    @Column(name = "humidity", precision = 5, scale = 2)
    private BigDecimal humidity;

    @Column(name = "wind_speed", precision = 5, scale = 2)
    private BigDecimal windSpeed;

    @Column(name = "pressure1", precision = 7, scale = 2)
    private BigDecimal pressure1;

    @Column(name = "pressure2", precision = 7, scale = 2)
    private BigDecimal pressure2;

    @Column(name = "other_environmental_conditions", columnDefinition = "TEXT")
    private String otherEnvironmentalConditions;

    @Column(name = "air_flow_conditions", length = 255)
    private String airFlowConditions;

    @Column(name = "operation_mode", length = 255)
    private String operationMode;

    @Column(name = "operation_break", length = 255)
    private String operationBreak;

    @Column(name = "local_air_extraction", length = 255)
    private String localAirExtraction;

    @Column(name = "serial_numbers_of_samples", length = 255)
    private String serialNumbersOfSamples;

    @ManyToOne
    @JoinColumn(name = "project_number", nullable = false, foreignKey = @ForeignKey(name = "fk_project_number"))
    private Project project;

    @Column(name = "status", length = 50, nullable = false)
    private String status = "active";

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
