package io.shashanksm.pms.dtos;

public record ParkingLotCreateRequest(
    String location,
    Integer floors
) {}