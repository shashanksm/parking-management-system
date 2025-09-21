package io.shashanksm.pms.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.shashanksm.pms.business.exceptions.NoParkingAvailableException;
import io.shashanksm.pms.dtos.ParkingLotEntryGateDto;
import io.shashanksm.pms.dtos.ParkingSlotDto;
import io.shashanksm.pms.dtos.RecommendedSlotResult;
import io.shashanksm.pms.dtos.VehicleDto;
import io.shashanksm.pms.repositories.ParkingSlotRepository;
import io.shashanksm.pms.repositories.jdbc.SlotAllocationRepository;

@Service
public class NearestAvailableParkingAllotmentStrategy implements ParkingAllotmentStrategy {

	@Autowired
	private SlotAllocationRepository slotAllocationRepository;

	@Autowired
	ParkingSlotRepository parkingSlotRepository;

	@Override
	public ParkingSlotDto assignParkingSlot(VehicleDto vehicleDto, ParkingLotEntryGateDto parkingLotEntryGateDto) {

		Long vehicleId = vehicleDto.id();
		Long parkingLotEntryGateId = parkingLotEntryGateDto.id();

		Optional<RecommendedSlotResult> recommendedSlotResult = slotAllocationRepository
				.findNearestAvailableSlot(vehicleId, parkingLotEntryGateId);
		
		if(recommendedSlotResult.isEmpty())
			throw new NoParkingAvailableException();
		
		long parkingSlotId = recommendedSlotResult.get().recommendedSlotId();
		
		ParkingSlotDto parkingSlotDto = ParkingSlotDto.fromEntity(parkingSlotRepository.findById(parkingSlotId).get());

		return parkingSlotDto;
	}

}
