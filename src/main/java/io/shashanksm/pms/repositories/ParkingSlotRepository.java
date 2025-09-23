package io.shashanksm.pms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.shashanksm.pms.entities.ParkingLot;
import io.shashanksm.pms.entities.ParkingSlot;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long>{
	public Optional<ParkingSlot> findByParkingLotAndFloorNumberAndSlotNumber(ParkingLot parkingLot, Integer floorNumber, Integer slotNumber);
}
