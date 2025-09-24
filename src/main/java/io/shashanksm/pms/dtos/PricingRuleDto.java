package io.shashanksm.pms.dtos;

import io.shashanksm.pms.entities.PricingRule;
import java.time.Instant;

public record PricingRuleDto(
    Long id,
    String name,
    String description,
    Integer vehicleType,
    Integer freeParkingHours,
    Double hourlyRate,
    Boolean active,
    Instant createdAt
) {
    public static PricingRuleDto fromEntity(PricingRule entity) {
        if (entity == null) {
            return null;
        }
        return new PricingRuleDto(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getVehicleType(),
            entity.getFreeParkingHours(),
            entity.getHourlyRate(),
            entity.getActive(),
            entity.getCreatedAt()
        );
    }
}