package io.shashanksm.pms.dtos;

import io.shashanksm.pms.entities.ParkingLotEntryGate;

public record ParkingLotEntryGateDto(
    Long id,
    int gateNumber,
    Long parkingLotId // Represents the foreign key ID
) {
    /**
     * Creates a DTO from a persistent ParkingLotEntryGate entity.
     */
    public static ParkingLotEntryGateDto fromEntity(ParkingLotEntryGate entity) {
        // Safely extract the parkingLot ID from the related entity
        Long lotId = entity.getParkingLot() != null ? entity.getParkingLot().getId() : null;

        return new ParkingLotEntryGateDto(
            entity.getId(),
            entity.getGateNumber(),
            lotId
        );
    }

    /**
     * Converts the DTO back to a bare-bones ParkingLotEntryGate entity.
     * NOTE: This requires the full ParkingLot entity object to be passed in
     * to correctly set the relationship before saving.
     */
    /*
    public ParkingLotEntryGate toEntity(io.shashanksm.pms.entities.ParkingLot parkingLotEntity) {
        ParkingLotEntryGate entity = new ParkingLotEntryGate();
        
        // Set fields from DTO
        entity.setId(this.id);
        entity.setGateNumber(this.gateNumber);
        
        // Set the relationship object
        entity.setParkingLot(parkingLotEntity);
        
        return entity;
    }
    */
}