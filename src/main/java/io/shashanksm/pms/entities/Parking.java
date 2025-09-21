package io.shashanksm.pms.entities;

import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "parking")
public class Parking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "active", nullable = false)
	private Boolean active;

	// --- Relationships (Foreign Keys) ---

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parking_slot", nullable = false)
	private ParkingSlot parkingSlot;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vehicle", nullable = false)
	private Vehicle vehicle;

	// --- Payment Fields ---

	@Column(name = "payment_status", nullable = false)
	private Boolean paymentStatus; // Mapped to BOOLEAN NOT NULL

	@Column(name = "payment_amount")
	private Double paymentAmount; // Mapped to DOUBLE PRECISION (Nullable)

	// --- Date/Time Fields ---

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@Column(name = "completed_at")
	private Instant completedAt; // Nullable

	// --- Constructors ---

	public Parking() {
		// Required by JPA
	}

	// Constructor for creating a new parking record (entry)
	public Parking(ParkingSlot parkingSlot, Vehicle vehicle) {
		this.parkingSlot = parkingSlot;
		this.vehicle = vehicle;
		this.active = true;
		this.paymentStatus = false; // Assume no payment attempted yet
		this.createdAt = Instant.now();
		// completedAt and paymentAmount remain null
	}
	
	

	public Parking(ParkingSlot parkingSlot, Vehicle vehicle, Instant createdAt) {
		super();
		this.parkingSlot = parkingSlot;
		this.vehicle = vehicle;
		this.active = true;
		this.paymentStatus = false;
		this.createdAt = createdAt;
	}

	// --- Getters and Setters ---

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public ParkingSlot getParkingSlot() {
		return parkingSlot;
	}

	public void setParkingSlot(ParkingSlot parkingSlot) {
		this.parkingSlot = parkingSlot;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Boolean getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(Instant completedAt) {
		this.completedAt = completedAt;
	}

	// --- Standard equals and hashCode (using ID) ---

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Parking))
			return false;
		return id != null && Objects.equals(id, ((Parking) o).id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "Parking [id=" + id + ", active=" + active + ", paymentStatus=" + paymentStatus + ", amount="
				+ paymentAmount + ", createdAt=" + createdAt + "]";
	}
}