package io.shashanksm.pms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.shashanksm.pms.entities.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{
	public List<Vehicle> findByVehicleNumber(String vehicleNumber);
}
