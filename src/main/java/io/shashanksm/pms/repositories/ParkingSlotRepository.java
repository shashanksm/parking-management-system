package io.shashanksm.pms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.shashanksm.pms.entities.ParkingSlot;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long>{

}
