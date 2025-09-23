package io.shashanksm.pms.entities;

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
@Table(name = "parking_slot")
public class ParkingSlot {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parking_lot", nullable = false)
	private ParkingLot parkingLot;

	@Column(name = "slot_number")
	private Integer slotNumber;

	@Column(name = "floor_number")
	private Integer floorNumber;
	
	@Column(name = "parking_type")
	private Integer parkingType;

	@Column(name = "available")
	private boolean available;

	

	public ParkingSlot(Long id, ParkingLot parkingLot, int slotNumber, int floorNumber, int parkingType,
			boolean available) {
		super();
		this.parkingLot = parkingLot;
		this.slotNumber = slotNumber;
		this.floorNumber = floorNumber;
		this.parkingType = parkingType;
		this.available = available;
	}

	public ParkingSlot() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ParkingLot getParkingLot() {
		return parkingLot;
	}

	public void setParkingLot(ParkingLot parkingLot) {
		this.parkingLot = parkingLot;
	}

	public Integer getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(int slotNumber) {
		this.slotNumber = slotNumber;
	}

	public Integer getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public boolean isStatus() {
		return available;
	}

	public void setStatus(boolean status) {
		this.available = status;
	}

	public Integer getParkingType() {
		return parkingType;
	}

	public void setParkingType(int parkingType) {
		this.parkingType = parkingType;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	
}
