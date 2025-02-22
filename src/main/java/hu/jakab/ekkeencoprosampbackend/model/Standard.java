package hu.jakab.ekkeencoprosampbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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

    @Column(name = "standard_type", length = 255)
    private String standardType;

    @Column(name = "identifier", length = 255, nullable = false, unique = true)
    private String identifier;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
