package hu.jakab.ekkeencoprosampbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "analytical_lab_reports", uniqueConstraints = {
        @UniqueConstraint(columnNames = "report_number")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticalLabReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "report_number", length = 50, nullable = false, unique = true)
    private String reportNumber;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @ManyToOne
    @JoinColumn(name = "laboratory_id", nullable = false, foreignKey = @ForeignKey(name = "fk_laboratory"))
    @JsonBackReference
    private Laboratory laboratory;

    @OneToMany(mappedBy = "labReport", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SampleAnalyticalResult> sampleResults = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
