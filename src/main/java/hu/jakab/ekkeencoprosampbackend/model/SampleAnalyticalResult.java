package hu.jakab.ekkeencoprosampbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sample_analytical_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleAnalyticalResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sample_id", nullable = false, foreignKey = @ForeignKey(name = "fk_sample"))
    private Sample sample;

    @ManyToOne
    @JoinColumn(name = "contaminant_id", nullable = false, foreignKey = @ForeignKey(name = "fk_contaminant"))
    private Contaminant contaminant;

    @Column(name = "result_main", precision = 10, scale = 4, nullable = false)
    private BigDecimal resultMain;

    @Column(name = "result_control", precision = 10, scale = 4)
    private BigDecimal resultControl;

    @Column(name = "result_main_control", precision = 10, scale = 4)
    private BigDecimal resultMainControl;

    @ManyToOne
    @JoinColumn(name = "measurement_unit", nullable = false, foreignKey = @ForeignKey(name = "fk_measurement_unit"))
    private MeasurementUnit measurementUnit;

    @Column(name = "detection_limit", precision = 10, scale = 4)
    private BigDecimal detectionLimit;

    @Column(name = "measurement_uncertainty", precision = 5, scale = 2)
    private BigDecimal measurementUncertainty;

    @Column(name = "analysis_method", length = 255)
    private String analysisMethod;

    @ManyToOne
    @JoinColumn(name = "lab_report_id", nullable = false, foreignKey = @ForeignKey(name = "fk_lab_report"))
    @JsonBackReference
    private AnalyticalLabReport labReport;

    @Column(name = "analysis_date")
    private LocalDateTime analysisDate;

    @Column(name = "calculated_concentration", precision = 10, scale = 4)
    private BigDecimal calculatedConcentration;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
