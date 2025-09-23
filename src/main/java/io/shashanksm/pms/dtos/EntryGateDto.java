package io.shashanksm.pms.dtos;

import io.shashanksm.pms.entities.ParkingLotEntryGate;

public record EntryGateDto(
    Long id,
    Long parkingLotId,
    String parkingLotName,
    Integer gateNumber
) {
    public static EntryGateDto fromEntity(ParkingLotEntryGate entity) {
        if (entity == null) {
            return null;
        }
        return new EntryGateDto(
            entity.getId(),
            entity.getParkingLot().getId(),
            entity.getParkingLot().getName(), // Changed from getLocation()
            entity.getGateNumber()
        );
    }
}