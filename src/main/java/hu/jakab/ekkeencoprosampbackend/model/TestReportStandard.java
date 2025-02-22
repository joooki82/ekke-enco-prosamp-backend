package hu.jakab.ekkeencoprosampbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "test_report_standards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestReportStandard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_report_id", nullable = false, foreignKey = @ForeignKey(name = "fk_test_report"))
    private TestReport testReport;

    @ManyToOne
    @JoinColumn(name = "standard_id", nullable = false, foreignKey = @ForeignKey(name = "fk_standard"))
    private Standard standard;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
