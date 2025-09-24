package io.shashanksm.pms.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.shashanksm.pms.dtos.ParkingTicket;
import io.shashanksm.pms.entities.Parking;
import io.shashanksm.pms.entities.Vehicle;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long>{

	//Optional<ParkingTicket> findByVehicleAndActive(Vehicle vehicle, boolean b);
	
}
