package io.shashanksm.pms.dtos;

public record ParkingSlotCreateRequest(
    Long parkingLotId,
    Integer slotNumber,
    Integer floorNumber,
    Integer parkingType,
    Boolean available
) {}