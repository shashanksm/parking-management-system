package io.shashanksm.pms.controllers.requests;

import java.time.Instant;

public record ParkingEntryRequest(
		
		//vehicle details
		String owner,
		String vehicleNumber,
		int vehicleType,
		
		//parking-lot details
		String location,
		int gateNumber,
		
		//entry details
		Instant entryTime
) {

}
