package hu.jakab.ekkeencoprosampbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "measurement_units", uniqueConstraints = {
        @UniqueConstraint(columnNames = "unit_code")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasurementUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "unit_code", length = 20, nullable = false, unique = true)
    private String unitCode; // e.g., "mg/m³", "ppm", "µg/L"

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description; // e.g., "Milligrams per cubic meter"

    @Column(name = "unit_category", length = 50, nullable = false)
    private String unitCategory; // e.g., "Concentration", "Mass", "Volume"

    @ManyToOne
    @JoinColumn(name = "base_unit_id", foreignKey = @ForeignKey(name = "fk_base_unit"))
    private MeasurementUnit baseUnit; // Self-reference for unit conversion

    @Column(name = "conversion_factor")
    private Double conversionFactor; // Factor to convert to base unit

    @Column(name = "standard_body", length = 50)
    private String standardBody; // e.g., "SI", "ISO", "ASTM", "EPA"

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
