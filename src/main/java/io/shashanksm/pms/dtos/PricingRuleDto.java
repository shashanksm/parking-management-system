package io.shashanksm.pms.dtos;

import io.shashanksm.pms.entities.PricingRule;
import java.time.Instant;

public record PricingRuleDto(
    Long id,
    String name,
    String description,
    Boolean active,
    Integer vehicleType,
    Integer freeParkingHours,
    Double hourlyRate,
    Instant createdAt
) {
    /**
     * Factory method to create a PricingRuleDto from a PricingRule entity.
     */
    public static PricingRuleDto fromEntity(PricingRule rule) {
        return new PricingRuleDto(
            rule.getId(),
            rule.getName(),
            rule.getDescription(),
            rule.getActive(),
            rule.getVehicleType(),
            rule.getFreeParkingHours(),
            rule.getHourlyRate(),
            rule.getCreatedAt()
        );
    }
    
    /**
     * Converts the DTO back to a bare-bones PricingRule entity.
     * This is useful for updates or saves, but note that the entity constructor
     * should handle default values for active, freeParkingHours, and createdAt.
     */
    public PricingRule toEntity() {
        // Use the all-arguments constructor if it exists, otherwise use a setter-based approach.
        // Assuming your PricingRule entity has a constructor matching the core fields:
        PricingRule entity = new PricingRule(
            this.name, 
            this.description, 
            this.vehicleType, 
            this.freeParkingHours, 
            this.hourlyRate
        );
        
        // Set ID and audit fields explicitly for updates
        entity.setId(this.id);
        entity.setActive(this.active);
        entity.setCreatedAt(this.createdAt);
        
        return entity;
    }
}