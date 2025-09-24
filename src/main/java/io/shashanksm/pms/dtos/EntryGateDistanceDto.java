package io.shashanksm.pms.dtos;

import io.shashanksm.pms.entities.EntryGateDistance;

public record EntryGateDistanceDto(
    Long id,
    Long parkingLotEntryGateId,
    Long parkingSlotId,
    Integer distance
) {
    public static EntryGateDistanceDto fromEntity(EntryGateDistance entity) {
        if (entity == null) {
            return null;
        }
        return new EntryGateDistanceDto(
            entity.getId(),
            entity.getParkingLotEntryGate().getId(),
            entity.getParkingSlot().getId(),
            entity.getDistance()
        );
    }
}