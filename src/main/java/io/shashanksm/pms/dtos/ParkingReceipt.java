package io.shashanksm.pms.dtos;

import java.time.Instant;
import java.util.Objects;

/**
 * Immutable class representing the final receipt/summary of a completed parking session.
 */
public final class ParkingReceipt {

    private final Long id;
    private final String owner;
    private final String vehicleNumber;
    private final String parkingLot;
    private final int entryGate;
    private final int slotNumber;
    private final Instant entryTime;
    private final Instant exitTime;          // NEW: Completion time
    private final Double paymentAmount;     // NEW: Final payment amount

    /**
     * Private constructor enforces creation via the static Builder.
     */
    private ParkingReceipt(Builder builder) {
        this.id = builder.id;
        this.owner = builder.owner;
        this.vehicleNumber = builder.vehicleNumber;
        this.parkingLot = builder.parkingLot;
        this.entryGate = builder.entryGate;
        this.slotNumber = builder.slotNumber;
        this.entryTime = builder.entryTime;
        this.exitTime = builder.exitTime;
        this.paymentAmount = builder.paymentAmount;
    }

    /**
     * Factory method to start the building process.
     */
    public static Builder builder() {
        return new Builder();
    }

    // --- Accessor Methods (Getters) ---

    public Long getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getParkingLot() {
        return parkingLot;
    }

    public int getEntryGate() {
        return entryGate;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public Instant getEntryTime() {
        return entryTime;
    }

    public Instant getExitTime() {
        return exitTime;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    // --- Standard Overrides ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingReceipt that = (ParkingReceipt) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ParkingReceipt{" +
                "id=" + id +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", entryTime=" + entryTime +
                ", exitTime=" + exitTime +
                '}';
    }

    // ========================================================================
    // BUILDER CLASS
    // ========================================================================

    public static class Builder {
        private Long id;
        private String owner;
        private String vehicleNumber;
        private String parkingLot;
        private int entryGate;
        private int slotNumber;
        private Instant entryTime;
        private Instant exitTime;
        private Double paymentAmount;

        private Builder() {}

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder owner(String owner) {
            this.owner = owner;
            return this;
        }

        public Builder vehicleNumber(String vehicleNumber) {
            this.vehicleNumber = vehicleNumber;
            return this;
        }

        public Builder parkingLot(String parkingLot) {
            this.parkingLot = parkingLot;
            return this;
        }

        public Builder entryGate(int entryGate) {
            this.entryGate = entryGate;
            return this;
        }

        public Builder slotNumber(int slotNumber) {
            this.slotNumber = slotNumber;
            return this;
        }

        public Builder entryTime(Instant entryTime) {
            this.entryTime = entryTime;
            return this;
        }

        public Builder exitTime(Instant exitTime) {
            this.exitTime = exitTime;
            return this;
        }

        public Builder paymentAmount(Double paymentAmount) {
            this.paymentAmount = paymentAmount;
            return this;
        }

        /**
         * Final build method that constructs the immutable ParkingReceipt instance.
         */
        public ParkingReceipt build() {
            // Strong validation for a final receipt
            if (id == null || vehicleNumber == null || entryTime == null || exitTime == null || paymentAmount == null) {
                throw new IllegalStateException("Receipt must have ID, Vehicle Number, Entry Time, Exit Time, and Payment Amount.");
            }
            
            return new ParkingReceipt(this);
        }
    }
}