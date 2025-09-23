package io.shashanksm.pms.dtos;

public record EntryGateDistanceCreateRequest(
    Long parkingLotEntryGateId,
    Long parkingSlotId,
    Integer distance
) {}