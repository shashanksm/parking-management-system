package io.shashanksm.pms.dtos;

public record EntryGateDistanceUpdateRequest(
    Long id,
    Long parkingLotEntryGateId,
    Long parkingSlotId,
    Integer distance
) {}