package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.Equipment;
import hu.jakab.ekkeencoprosampbackend.model.SamplingRecordDatM200;
import hu.jakab.ekkeencoprosampbackend.model.SamplingRecordEquipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SamplingRecordEquipmentRepository extends JpaRepository<SamplingRecordEquipment, Long> {

    List<SamplingRecordEquipment> findBySamplingRecord(SamplingRecordDatM200 samplingRecord);

    List<SamplingRecordEquipment> findByEquipment(Equipment equipment);

    boolean existsBySamplingRecordAndEquipment(SamplingRecordDatM200 samplingRecord, Equipment equipment);

    void deleteBySamplingRecordAndEquipment(SamplingRecordDatM200 samplingRecord, Equipment equipment);

}
