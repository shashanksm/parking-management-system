package io.shashanksm.pms.controllers;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.shashanksm.pms.controllers.requests.ParkingReceiptRequest;
import io.shashanksm.pms.dtos.ParkingReceipt;
import io.shashanksm.pms.services.ParkingPaymentService;

@RestController
@RequestMapping("/api/receipts")
public class ParkingReceiptController {
	
	private static final Logger log = LoggerFactory.getLogger(ParkingReceiptController.class);
	
	private ParkingPaymentService parkingPaymentService;
	
	public ParkingReceiptController(ParkingPaymentService parkingPaymentService) {
		this.parkingPaymentService = parkingPaymentService;
	}
	
	@PostMapping
	public ResponseEntity<ParkingReceipt> generateReceipt(@RequestBody ParkingReceiptRequest parkingReceiptRequest) {
		
		long parkingId = parkingReceiptRequest.id();
		Instant exitTime = parkingReceiptRequest.exitTime();
		
		ParkingReceipt parkingReceipt = parkingPaymentService.createParkingReceipt(parkingId, exitTime);
		
		return ResponseEntity.ok(parkingReceipt);
	}
}