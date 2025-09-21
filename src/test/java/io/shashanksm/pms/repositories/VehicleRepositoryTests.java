package io.shashanksm.pms.repositories;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.shashanksm.pms.entities.Vehicle;

public class VehicleRepositoryTests extends BaseRepositoryTests {
	
	private static final Logger log = LoggerFactory.getLogger(VehicleRepositoryTests.class);
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	
	@Test
	public void shouldCreateVehicle() {
		Vehicle requested = new Vehicle(100L, "MH12GH1001", "test_user", 3);
		
		Vehicle created = vehicleRepository.save(requested);
		
		assertEquals(requested.getOwner(), created.getOwner());
		assertEquals(requested.getVehicleNumber(), created.getVehicleNumber());
		
		log.info(created.toString());
	}
	
	@Test
	public void shouldRetrieveVehicleByVehicleNumber() {
		String expected = "MH12VV5072";
		List<Vehicle> vehicles = vehicleRepository.findByVehicleNumber(expected);
		vehicles.forEach(vehicle -> {
			String actual = vehicle.getVehicleNumber();
			assertEquals(expected, actual);
		});
	}
}
