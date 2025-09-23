package io.shashanksm.pms.api;

import io.shashanksm.pms.dtos.ParkingLotDto;
import io.shashanksm.pms.services.ParkingLotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/parking-lots")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @PostMapping
    public ResponseEntity<ParkingLotDto> createParkingLot(@RequestBody ParkingLotDto parkingLotDto) {
        ParkingLotDto createdParkingLot = parkingLotService.createParkingLot(parkingLotDto);
        return new ResponseEntity<>(createdParkingLot, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingLotDto> getParkingLotById(@PathVariable Long id) {
        ParkingLotDto parkingLotDto = parkingLotService.getParkingLotById(id);
        return ResponseEntity.ok(parkingLotDto);
    }

    @GetMapping
    public ResponseEntity<List<ParkingLotDto>> getAllParkingLots() {
        List<ParkingLotDto> parkingLots = parkingLotService.getAllParkingLots();
        return ResponseEntity.ok(parkingLots);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingLotDto> updateParkingLot(@PathVariable Long id, @RequestBody ParkingLotDto parkingLotDto) {
        ParkingLotDto updatedParkingLot = parkingLotService.updateParkingLot(id, parkingLotDto);
        return ResponseEntity.ok(updatedParkingLot);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingLot(@PathVariable Long id) {
        parkingLotService.deleteParkingLot(id);
        return ResponseEntity.noContent().build();
    }
}