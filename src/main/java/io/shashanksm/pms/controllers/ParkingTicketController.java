package io.shashanksm.pms.controllers;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.shashanksm.pms.controllers.requests.ParkingEntryRequest;
import io.shashanksm.pms.dtos.ParkingTicket;
import io.shashanksm.pms.services.ParkingTicketService;

@RestController
@RequestMapping("/api/parking-tickets")
public class ParkingTicketController {
	
	private static final Logger log = LoggerFactory.getLogger(ParkingTicketController.class);
	
	private ParkingTicketService parkingTicketService;
	
	public ParkingTicketController(ParkingTicketService parkingTicketService) {
		this.parkingTicketService = parkingTicketService;
	}
	
	@PostMapping
	public ResponseEntity<ParkingTicket> issueParkingTicket(@RequestBody ParkingEntryRequest parkingEntryRequest) {
		Optional<ParkingTicket> parkingTicketOpt = parkingTicketService.issueParkingTicket(parkingEntryRequest);
		if(parkingTicketOpt.isEmpty()) {
			log.error("parking ticket could not be created");
		}
		
		ParkingTicket parkingTicket = parkingTicketOpt.get();
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(parkingTicket.getId())
                .toUri();
		
		return ResponseEntity.created(location).body(parkingTicket);
	}
	
	@GetMapping("/{ticketId}")
	public ResponseEntity<ParkingTicket> getParkingTicket(@PathVariable("ticketId") Long ticketId) {
		return ResponseEntity.ok(parkingTicketService.generateParkingTicketByParkingId(ticketId));
	}
}
