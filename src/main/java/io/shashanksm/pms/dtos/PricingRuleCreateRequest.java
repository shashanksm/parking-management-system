package io.shashanksm.pms.dtos;

public record PricingRuleCreateRequest(
    String name,
    String description,
    Integer vehicleType,
    Integer freeParkingHours,
    Double hourlyRate
) {}