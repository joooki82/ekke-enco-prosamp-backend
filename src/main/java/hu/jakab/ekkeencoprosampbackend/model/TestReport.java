package hu.jakab.ekkeencoprosampbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "test_reports", uniqueConstraints = {
        @UniqueConstraint(columnNames = "report_number")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "report_number", length = 50, nullable = false, unique = true)
    private String reportNumber;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "approved_by", foreignKey = @ForeignKey(name = "fk_approved_by"))
    private User approvedBy;

    @ManyToOne
    @JoinColumn(name = "prepared_by", foreignKey = @ForeignKey(name = "fk_prepared_by"))
    private User preparedBy;

    @ManyToOne
    @JoinColumn(name = "checked_by", foreignKey = @ForeignKey(name = "fk_checked_by"))
    private User checkedBy;

    @Column(name = "aim_of_test", columnDefinition = "TEXT")
    private String aimOfTest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, foreignKey = @ForeignKey(name = "fk_project"))
    private Project project;

    @OneToMany(mappedBy = "testReport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestReportStandard> testReportStandards = new ArrayList<>();

    @OneToMany(mappedBy = "testReport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestReportSampler> testReportSamplers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false, foreignKey = @ForeignKey(name = "fk_location"))
    private Location location;

    @ManyToOne
    @JoinColumn(name = "sampling_record_id", nullable = false, foreignKey = @ForeignKey(name = "fk_sampling_record"))
    private SamplingRecordDatM200 samplingRecord;

    @Column(name = "technology", columnDefinition = "TEXT")
    private String technology;

    @Column(name = "sampling_conditions_dates", columnDefinition = "TEXT")
    private String samplingConditionsDates;

    @Column(name = "determination_of_pollutant_concentration", columnDefinition = "TEXT")
    private String determinationOfPollutantConcentration;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_status", length = 20, nullable = false)
    private TestReportStatus reportStatus = TestReportStatus.DRAFT;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
