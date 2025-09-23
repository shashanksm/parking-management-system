package io.shashanksm.pms.dtos;

public record EntryGateCreateRequest(
    Long parkingLotId,
    Integer gateNumber
) {}