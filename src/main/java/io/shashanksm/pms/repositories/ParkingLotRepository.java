package io.shashanksm.pms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.shashanksm.pms.entities.ParkingLot;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long>{
	public Optional<ParkingLot> findByName(String name);
}
