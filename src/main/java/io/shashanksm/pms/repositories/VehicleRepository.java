package io.shashanksm.pms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.shashanksm.pms.entities.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>{
	public List<Vehicle> findByVehicleNumber(String vehicleNumber);
}
