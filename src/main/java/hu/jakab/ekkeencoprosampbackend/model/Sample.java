package hu.jakab.ekkeencoprosampbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "samples", uniqueConstraints = {
        @UniqueConstraint(columnNames = "sample_identifier")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sampling_record_id", nullable = false, foreignKey = @ForeignKey(name = "fk_samples_sampling_record"))
    private SamplingRecordDatM200 samplingRecord;

    @Column(name = "sample_identifier", length = 50, nullable = false)
    private String sampleIdentifier;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "employee_name", length = 255)
    private String employeeName;

    @Column(name = "temperature", precision = 5, scale = 2)
    private BigDecimal temperature;

    @Column(name = "humidity", precision = 5, scale = 2)
    private BigDecimal humidity;

    @Column(name = "pressure", precision = 7, scale = 2)
    private BigDecimal pressure;

    @Column(name = "sample_volume_flow_rate", precision = 5, scale = 4)
    private BigDecimal sampleVolumeFlowRate;

    @ManyToOne
    @JoinColumn(name = "sample_volume_flow_rate_unit", nullable = false, foreignKey = @ForeignKey(name = "fk_measurement_unit"))
    private MeasurementUnit sampleVolumeFlowRateUnit;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "sample_type", length = 10, nullable = false)
    private String sampleType = "AK";

    @Column(name = "status", length = 50, nullable = false)
    private String status = "ACTIVE";

    @Column(name = "remarks", length = 255)
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "sampling_type_id", foreignKey = @ForeignKey(name = "fk_sampling_type"))
    private SamplingType samplingType;

    @ManyToOne
    @JoinColumn(name = "adjustment_method_id", foreignKey = @ForeignKey(name = "fk_adjustment_method"))
    private AdjustmentMethod adjustmentMethod;

    @Column(name = "sampling_flow_rate", precision = 6, scale = 3)
    private BigDecimal samplingFlowRate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
