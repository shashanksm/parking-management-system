package io.shashanksm.pms.dtos;

import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ParkingReceiptTest {

    @Test
    void builderCreatesValidReceipt() {
        Instant entry = Instant.now();
        Instant exit = entry.plusSeconds(3600);

        ParkingReceipt receipt = ParkingReceipt.builder()
                .id(1L)
                .owner("Rajesh Sharma")
                .vehicleNumber("MH01AA7777")
                .parkingLot("small_mall_1")
                .slotNumber(101)
                .entryTime(entry)
                .exitTime(exit)
                .paymentAmount(50.0)
                .build();

        assertEquals(1L, receipt.getId());
        assertEquals("Rajesh Sharma", receipt.getOwner());
        assertEquals("MH01AA7777", receipt.getVehicleNumber());
        assertEquals("small_mall_1", receipt.getParkingLot());
        assertEquals(101, receipt.getSlotNumber());
        assertEquals(entry, receipt.getEntryTime());
        assertEquals(exit, receipt.getExitTime());
        assertEquals(50.0, receipt.getPaymentAmount());
    }

    @Test
    void builderThrowsOnMissingFields() {
        assertThrows(IllegalStateException.class, () -> {
            ParkingReceipt.builder()
                    .id(1L)
                    .vehicleNumber("MH01AA7777")
                    // missing entryTime, exitTime, paymentAmount
                    .build();
        });
    }
}