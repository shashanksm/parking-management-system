package io.shashanksm.pms.repositories;

import io.shashanksm.pms.entities.EntryGateDistance;
import io.shashanksm.pms.entities.ParkingLotEntryGate;
import io.shashanksm.pms.entities.ParkingSlot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntryGateDistanceRepository extends JpaRepository<EntryGateDistance, Long> {

    /**
     * Finds all distance records associated with a specific entry gate, 
     * ordered by the shortest distance first.
     * * This is useful for finding the nearest available parking slot from a gate.
     * * Spring Data JPA query generation:
     * SELECT d FROM EntryGateDistance d 
     * WHERE d.parkingLotEntryGate.id = ?1 
     * ORDER BY d.distance ASC
     */
    List<EntryGateDistance> findByParkingLotEntryGateIdOrderByDistanceAsc(Long entryGateId);

    /**
     * Finds the distance between a specific entry gate and a specific parking slot.
     */
    EntryGateDistance findByParkingLotEntryGateIdAndParkingSlotId(Long entryGateId, Long parkingSlotId);
    public Optional<EntryGateDistance> findByParkingLotEntryGateAndParkingSlot(ParkingLotEntryGate parkingLotEntryGate, ParkingSlot parkingSlot);
}