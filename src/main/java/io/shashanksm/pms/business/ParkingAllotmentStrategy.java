package io.shashanksm.pms.business;

import io.shashanksm.pms.dtos.ParkingLotEntryGateDto;
import io.shashanksm.pms.dtos.ParkingSlotDto;
import io.shashanksm.pms.dtos.VehicleDto;

public interface ParkingAllotmentStrategy {
	public ParkingSlotDto assignParkingSlot(VehicleDto vehicleDto, ParkingLotEntryGateDto parkingLotEntryGateDto);
}
