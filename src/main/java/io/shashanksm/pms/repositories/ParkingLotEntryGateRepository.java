package io.shashanksm.pms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.shashanksm.pms.entities.ParkingLot;
import io.shashanksm.pms.entities.ParkingLotEntryGate;

@Repository
public interface ParkingLotEntryGateRepository extends JpaRepository<ParkingLotEntryGate, Long>{
	public List<ParkingLotEntryGate> findByParkingLot(ParkingLot parkingLot);
	
	 /**
     * Finds a single ParkingLotEntryGate based on the associated ParkingLot's location 
     * and the gate's number.
     * * Spring Data JPA generates the query:
     * SELECT pleg FROM ParkingLotEntryGate pleg 
     * WHERE pleg.parkingLot.location = ?1 AND pleg.gateNumber = ?2
     */
    public Optional<ParkingLotEntryGate> findByParkingLot_NameAndGateNumber(String name, Integer gateNumber);
    
    /**
     * Finds a single ParkingLotEntryGate by its associated ParkingLot entity 
     * and its gate number.
     * * @param parkingLot The fully loaded ParkingLot entity.
     * @param gateNumber The number of the gate (e.g., 1, 2, 3).
     * @return An Optional containing the matching gate, or empty if not found.
     */
    public Optional<ParkingLotEntryGate> findByParkingLotAndGateNumber(ParkingLot parkingLot, Integer gateNumber);
}
