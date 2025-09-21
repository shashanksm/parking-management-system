package io.shashanksm.pms.dtos;

import io.shashanksm.pms.entities.Vehicle;

public record VehicleDto(Long id, String vehicleNumber, String owner, int type) {
	public Vehicle toEntity() {
		return new Vehicle(id, vehicleNumber, owner, type);
	}

	public static VehicleDto fromEntity(Vehicle vehicle) {
		return new VehicleDto(vehicle.getId(), vehicle.getVehicleNumber(), vehicle.getOwner(), vehicle.getType());
	}
}
