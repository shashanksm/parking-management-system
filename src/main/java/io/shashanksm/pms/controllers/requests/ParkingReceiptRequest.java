package io.shashanksm.pms.controllers.requests;

import java.time.Instant;

//{
//    "id": 3,
//    "owner": "test_user_7",
//    "vehicleNumber": "MH12BF0002",
//    "parkingLot": "small_mall_1",
//    "entryGate": 2,
//    "slotNumber": 203,
//    "entryTime": "2025-09-21T11:54:26Z"
//}

public record ParkingReceiptRequest(
		Long id,
		Instant exitTime
) {
	
}
