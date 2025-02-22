package hu.jakab.ekkeencoprosampbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "sampling_record_equipments", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"fk_sampling_record_id", "fk_equipment_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SamplingRecordEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_sampling_record_id", nullable = false, foreignKey = @ForeignKey(name = "fk_sampling_record"))
    private SamplingRecordDatM200 samplingRecord;

    @ManyToOne
    @JoinColumn(name = "fk_equipment_id", nullable = false, foreignKey = @ForeignKey(name = "fk_equipment"))
    private Equipment equipment;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

