package io.shashanksm.pms.dtos;

import io.shashanksm.pms.entities.EntryGateDistance;

/**
 * A DTO record for the EntryGateDistance entity.
 * It contains the ID and the raw distance value.
 * Since the associated entities are often loaded separately, this record
 * simplifies data transfer by avoiding complex object graphs.
 */
public record EntryGateDistanceDto(
    Long id,
    Long parkingLotEntryGateId,
    Long parkingSlotId,
    Integer distance
) {
    /**
     * Factory method to create a DTO from an EntryGateDistance entity.
     * @param entity The EntryGateDistance entity.
     * @return A new EntryGateDistanceDto record.
     */
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