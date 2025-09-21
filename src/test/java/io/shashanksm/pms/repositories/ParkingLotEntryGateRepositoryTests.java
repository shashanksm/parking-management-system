package io.shashanksm.pms.repositories;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.shashanksm.pms.entities.ParkingLot;
import io.shashanksm.pms.entities.ParkingLotEntryGate;

public class ParkingLotEntryGateRepositoryTests extends BaseRepositoryTests{
	
	private static final Logger log = LoggerFactory.getLogger(ParkingLotRepositoryTests.class);
	
	@Autowired
	private ParkingLotRepository parkingLotRepository;
	
	@Autowired 
	ParkingLotEntryGateRepository parkingLotEntryGateRepository;
	
	@Test
	public void shouldRetrieveParkingLotEntryGatesByParkingLot() {
		ParkingLot parkingLot = parkingLotRepository.findById(1L).get();
		List<ParkingLotEntryGate> parkingLotEntryGates = parkingLotEntryGateRepository.findByParkingLot(parkingLot);
		parkingLotEntryGates.forEach(parkingLotEntryGate -> {
			log.info(parkingLotEntryGate.toString());
			assertEquals(parkingLot, parkingLotEntryGate.getParkingLot());
		});
	}
	
}
