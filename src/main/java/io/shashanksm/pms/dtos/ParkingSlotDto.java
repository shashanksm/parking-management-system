package io.shashanksm.pms.dtos;

import io.shashanksm.pms.entities.ParkingSlot;

public record ParkingSlotDto(
    Long id,
    Long parkingLotId,
    String parkingLotName,
    Integer slotNumber,
    Integer floorNumber,
    Integer parkingType,
    Boolean available
) {
    public static ParkingSlotDto fromEntity(ParkingSlot entity) {
        if (entity == null) {
            return null;
        }
        return new ParkingSlotDto(
            entity.getId(),
            entity.getParkingLot().getId(),
            entity.getParkingLot().getName(),
            entity.getSlotNumber(),
            entity.getFloorNumber(),
            entity.getParkingType(),
            entity.isAvailable()
        );
    }
}