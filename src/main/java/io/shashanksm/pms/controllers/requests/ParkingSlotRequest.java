package io.shashanksm.pms.controllers.requests;

public record ParkingSlotRequest(
		//parking slot details
		String parkingLotName,
		int slotNumber,
		int floorNumber,
		int parkingType,
		boolean available
) {
	
}
