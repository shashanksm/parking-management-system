package io.shashanksm.pms.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.shashanksm.pms.entities.Parking;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long>{
	
}
