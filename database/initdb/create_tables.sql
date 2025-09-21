-- File: ./initdb/create_tables.sql

CREATE TABLE IF NOT EXISTS parking_lot (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    floors INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS vehicle (
    id BIGSERIAL PRIMARY KEY,
    vehicle_number VARCHAR(255) NOT NULL,
    owner VARCHAR(255) NOT NULL,
    type INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS parking_slot (
    id BIGSERIAL PRIMARY KEY,
    parking_lot BIGINT NOT NULL,
    slot_number INTEGER NOT NULL,
    floor_number INTEGER NOT NULL,
    parking_type INTEGER NOT NULL,
    available BOOLEAN NOT NULL,
    CONSTRAINT fk_parking_lot
      FOREIGN KEY(parking_lot) 
      REFERENCES parking_lot(id)
      ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS parking_lot_entry_gate (
    id BIGSERIAL PRIMARY KEY,
    parking_lot BIGINT NOT NULL,
    gate_number INTEGER,

    CONSTRAINT fk_parking_lot
        FOREIGN KEY (parking_lot)
        REFERENCES parking_lot (id)
);

CREATE TABLE IF NOT EXISTS parking (
    id BIGSERIAL PRIMARY KEY,
    
    -- Transaction Status
    active BOOLEAN NOT NULL,
    
    -- Foreign Keys
    parking_slot BIGINT NOT NULL,
    vehicle BIGINT NOT NULL,
    
    -- Payment Fields (NEWLY ADDED)
    payment_status BOOLEAN NOT NULL,     -- Tracks if payment was successful (TRUE/FALSE)
    payment_amount DOUBLE PRECISION,     -- Stores the final calculated amount (Nullable)
    
    -- Date/Time Fields
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    completed_at TIMESTAMP WITH TIME ZONE,

    -- Define Foreign Key Constraints
    CONSTRAINT fk_parking_slot
        FOREIGN KEY (parking_slot)
        REFERENCES parking_slot (id),
    
    CONSTRAINT fk_vehicle
        FOREIGN KEY (vehicle)
        REFERENCES vehicle (id)
);

CREATE TABLE IF NOT EXISTS pricing_rule (
    -- Primary Key
    id BIGSERIAL PRIMARY KEY,
    
    -- Text Fields
    name VARCHAR(255) NOT NULL,
    description TEXT, -- Use TEXT for longer descriptions
    
    -- Configuration Fields
    vehicle_type INTEGER NOT NULL,
    free_parking_hours INTEGER NOT NULL DEFAULT 0,
    hourly_rate DOUBLE PRECISION NOT NULL,
    
    -- Status and Audit Fields
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS entry_gate_distance (
    -- Primary Key
    id BIGSERIAL PRIMARY KEY,
    
    -- Foreign Key to the Entry Gate table
    parking_lot_entry_gate BIGINT NOT NULL,
    
    -- Foreign Key to the Parking Slot table
    parking_slot BIGINT NOT NULL,
    
    -- The physical distance measurement
    distance INTEGER NOT NULL,
    
    -- Define the Foreign Key Constraints
    -- Assumes the primary key in parking_lot_entry_gate is 'id'
    CONSTRAINT fk_entry_gate
        FOREIGN KEY (parking_lot_entry_gate)
        REFERENCES parking_lot_entry_gate (id),
    
    -- Assumes the primary key in parking_slot is 'id'
    CONSTRAINT fk_parking_slot
        FOREIGN KEY (parking_slot)
        REFERENCES parking_slot (id),
        
    -- Optional: Enforce that the combination of gate and slot is unique.
    CONSTRAINT uq_gate_slot
        UNIQUE (parking_lot_entry_gate, parking_slot)
);