package io.shashanksm.pms.dtos;

import java.time.Instant;

// Uses Long for IDs and Double for the nullable payment amount
public record ParkingDto (
    Long id,
    Boolean active,
    
    // Foreign Key IDs
    Long parkingSlotId,
    Long vehicleId,
    
    // Payment Details
    Boolean paymentStatus,
    Double paymentAmount, // Must be the object Double to handle potential null values
    
    // Date/Time
    Instant createdAt,
    Instant completedAt // Nullable
) {
    // --- Conversion Methods ---

    /**
     * Creates a ParkingDto from a persistent Parking entity.
     * Extracts IDs from the related entities (ParkingSlot and Vehicle).
     */
    public static ParkingDto fromEntity(io.shashanksm.pms.entities.Parking parking) {
        // Guard against null relationships if the entity isn't fully loaded, 
        // though typically in this context, they should be present.
        Long slotId = parking.getParkingSlot() != null ? parking.getParkingSlot().getId() : null;
        Long vehicleId = parking.getVehicle() != null ? parking.getVehicle().getId() : null;
        
        return new ParkingDto(
            parking.getId(),
            parking.getActive(),
            slotId,
            vehicleId,
            parking.getPaymentStatus(),
            parking.getPaymentAmount(),
            parking.getCreatedAt(),
            parking.getCompletedAt()
        );
    }

    /**
     * Converts the DTO back to a bare-bones Parking entity for updates or saves.
     * NOTE: This method is less common for relationships. Typically, the service 
     * layer loads the ParkingSlot and Vehicle entities first before mapping to a Parking entity.
     */
    /*
    public io.shashanksm.pms.entities.Parking toEntity(
        io.shashanksm.pms.entities.ParkingSlot slotEntity,
        io.shashanksm.pms.entities.Vehicle vehicleEntity
    ) {
        io.shashanksm.pms.entities.Parking entity = new io.shashanksm.pms.entities.Parking();
        
        entity.setId(this.id);
        entity.setActive(this.active);
        
        // Relationships must be set using the full entity objects
        entity.setParkingSlot(slotEntity); 
        entity.setVehicle(vehicleEntity);
        
        entity.setPaymentStatus(this.paymentStatus);
        entity.setPaymentAmount(this.paymentAmount);
        entity.setCreatedAt(this.createdAt);
        entity.setCompletedAt(this.completedAt);
        
        return entity;
    }
    */
}