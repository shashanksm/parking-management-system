package io.shashanksm.pms.dtos;

import io.shashanksm.pms.entities.ParkingLot;

public record ParkingLotDto(
	Long id,
	String name,
	int floors
) {
	public ParkingLot toEntity() {
		return new ParkingLot(id, name, floors);
	}
	
	public static ParkingLotDto fromEntity(ParkingLot parkingLot) {
		return new ParkingLotDto(parkingLot.getId(), parkingLot.getName(), parkingLot.getFloors());
	}
}
