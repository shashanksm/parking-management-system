package io.shashanksm.pms.dtos;

// This DTO holds the specific fields returned by the SQL query
public record RecommendedSlotResult(
    Long recommendedSlotId,
    Integer slotNumber,
    Integer floorNumber,
    Integer distance
) {}