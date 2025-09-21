package io.shashanksm.pms.business;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.shashanksm.pms.dtos.ParkingLotEntryGateDto;
import io.shashanksm.pms.dtos.ParkingSlotDto;
import io.shashanksm.pms.dtos.VehicleDto;
import io.shashanksm.pms.entities.ParkingLotEntryGate;
import io.shashanksm.pms.entities.ParkingSlot;
import io.shashanksm.pms.entities.Vehicle;
import io.shashanksm.pms.repositories.BaseRepositoryTests;
import io.shashanksm.pms.repositories.ParkingLotEntryGateRepository;
import io.shashanksm.pms.repositories.ParkingSlotRepository;
import io.shashanksm.pms.repositories.VehicleRepository;
import io.shashanksm.pms.repositories.jdbc.SlotAllocationRepository;

public class SlotAllocationLogicTests extends BaseRepositoryTests {

	@Autowired
	private ParkingSlotRepository parkingSlotRepository;

	@Autowired
	private SlotAllocationRepository slotAllocRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private ParkingLotEntryGateRepository parkingLotEntryGateRepository;

	@Autowired
	private NearestAvailableParkingAllotmentStrategy nearestAvailableParkingAllotmentStrategy;

	@Test
	public void testAllocationLogic() {
		
		//1. choose a parkingLotEntryGate
		
		ParkingLotEntryGateDto parkingLotEntryGateDto = ParkingLotEntryGateDto.fromEntity(parkingLotEntryGateRepository.findById(1L).get());
		
		System.out.println(parkingLotEntryGateDto.toString());
		
		for(int i = 0; i<5; i++) {
			
			System.out.println("iteration - "+i);
			
			//2 create a vehicle
			VehicleDto vehicleDto = new VehicleDto(1L, "ooooooo", "aaaaaaaaaa", 1);
			
			//3 get nearest location
			ParkingSlotDto parkingSlotDto = nearestAvailableParkingAllotmentStrategy.assignParkingSlot(vehicleDto, parkingLotEntryGateDto);
			System.out.println("nearest slot = "+parkingSlotDto.toString());
			
			//4 make it unavailable
			ParkingSlot parkingSlot = parkingSlotRepository.findById(parkingSlotDto.id()).get();
			
			parkingSlot.setAvailable(false);
			
			parkingSlotRepository.save(parkingSlot);
			
		}
		
		assertTrue(true);
	
		
	}

}
