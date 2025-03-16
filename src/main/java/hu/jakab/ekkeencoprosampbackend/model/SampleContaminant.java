package hu.jakab.ekkeencoprosampbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "sample_contaminants",
        uniqueConstraints = @UniqueConstraint(columnNames = {"fk_sample_id", "fk_contaminant_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleContaminant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_sample_id", nullable = false, foreignKey = @ForeignKey(name = "fk_sample_contaminants_sample"))
    private Sample sample;

    @ManyToOne
    @JoinColumn(name = "fk_contaminant_id", nullable = false, foreignKey = @ForeignKey(name = "fk_sample_contaminants_contaminant"))
    private Contaminant contaminant;

    @OneToOne(mappedBy = "sampleContaminant", cascade = CascadeType.ALL, orphanRemoval = true)
    private SampleAnalyticalResult analyticalResult;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at", updatable = false)
    private LocalDateTime updatedAt;
}
