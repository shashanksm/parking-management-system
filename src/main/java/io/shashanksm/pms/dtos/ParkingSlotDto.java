package io.shashanksm.pms.dtos;

import io.shashanksm.pms.entities.ParkingSlot;

public record ParkingSlotDto(Long id,

		ParkingLotDto parkingLotDto,

		int slotNumber,

		int floorNumber,

		int parkingType,

		boolean available

) {

	public static ParkingSlotDto fromEntity(ParkingSlot parkingSlot) {
		return new ParkingSlotDto(
				parkingSlot.getId(), 
				ParkingLotDto.fromEntity(parkingSlot.getParkingLot()),
				parkingSlot.getSlotNumber(), 
				parkingSlot.getFloorNumber(), 
				parkingSlot.getParkingType(),
				parkingSlot.isAvailable()
			);
	}
	
	public ParkingSlot toEntity() {
		return new ParkingSlot(id, parkingLotDto.toEntity(), slotNumber, floorNumber, parkingType, available);
	}
}
