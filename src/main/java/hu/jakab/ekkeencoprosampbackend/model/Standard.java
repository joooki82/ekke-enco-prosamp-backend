package hu.jakab.ekkeencoprosampbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "standards", uniqueConstraints = {
        @UniqueConstraint(columnNames = "identifier")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Standard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "standard_number", length = 255, nullable = false)
    private String standardNumber;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING) // Store enum as string in DB
    @Column(name = "standard_type", length = 50, nullable = false)
    private StandardType standardType;

    @Column(name = "identifier", length = 255, nullable = false, unique = true)
    private String identifier;

    @OneToMany(mappedBy = "standard", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<LaboratoryStandard> laboratoryStandards = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
