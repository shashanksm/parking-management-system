package io.shashanksm.pms.repositories;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import io.shashanksm.pms.entities.ParkingLot;


public class ParkingLotRepositoryTests extends BaseRepositoryTests{
	
	private static final Logger log = LoggerFactory.getLogger(ParkingLotRepositoryTests.class);
	
	@Autowired
	private ParkingLotRepository parkingLotRepository;

	@Test
	public void shouldCreateAPArkingLot() {
		ParkingLot parkingLot = new ParkingLot(100L, "test_name", 100);
		log.info("requested parking lot = "+parkingLot.toString());
		
		ParkingLot created = parkingLotRepository.save(parkingLot);
		log.info(created.toString());
		
		assertEquals(parkingLot.getFloors(), created.getFloors());
		assertEquals(parkingLot.getName(), created.getName());
	}
}
