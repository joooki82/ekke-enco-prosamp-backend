package hu.jakab.ekkeencoprosampbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "test_report_samplers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"fk_test_report_id", "fk_user_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestReportSampler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_test_report_id", nullable = false, foreignKey = @ForeignKey(name = "fk_test_report"))
    private TestReport testReport;

    @ManyToOne
    @JoinColumn(name = "fk_user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user"))
    private User user;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
