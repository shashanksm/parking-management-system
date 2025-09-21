package io.shashanksm.pms.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.shashanksm.pms.entities.Parking;
import io.shashanksm.pms.entities.ParkingSlot;

public class ParkingRepositoryTests extends BaseRepositoryTests{
	
	private static final Logger log = LoggerFactory.getLogger(ParkingLotRepositoryTests.class);
	
	@Autowired
	private ParkingRepository parkingRepository;
	
	@Autowired
	private ParkingSlotRepository parkingSlotRepository;
	
	@Test
	public void shouldRetrieveParking() {
		Parking parking = parkingRepository.findById(1L).get();
		ParkingSlot parkingSlot = parkingSlotRepository.findById(12L).get();
		log.info(parking.toString());
		assertEquals(parking.getParkingSlot().getId(), parkingSlot.getId());
	}
}
