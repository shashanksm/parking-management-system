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


-- data --
/*
INSERT INTO public.parking_lot (id, "name", floors) VALUES(nextval('parking_lot_id_seq'::regclass), 'Pune_Airport_1', 5);
INSERT INTO public.parking_lot (id, "name", floors) VALUES(nextval('parking_lot_id_seq'::regclass), 'Pune_Station_1', 1);
INSERT INTO public.parking_lot (id, "name", floors) VALUES(nextval('parking_lot_id_seq'::regclass), 'Pune_Big_Mall_1', 3);

INSERT INTO parking_lot_entry_gate (parking_lot, gate_number) VALUES (1, 1);
INSERT INTO parking_lot_entry_gate (parking_lot, gate_number) VALUES (1, 2);
INSERT INTO parking_lot_entry_gate (parking_lot, gate_number) VALUES (1, 3);
INSERT INTO parking_lot_entry_gate (parking_lot, gate_number) VALUES (1, 4);
INSERT INTO parking_lot_entry_gate (parking_lot, gate_number) VALUES (1, 5);

INSERT INTO public.vehicle (id, vehicle_number, "owner", "type") VALUES(nextval('vehicle_id_seq'::regclass), 'MH14BF1212', 'test_user_0', 1); --1
INSERT INTO public.vehicle (id, vehicle_number, "owner", "type") VALUES(nextval('vehicle_id_seq'::regclass), 'MH12VV5072', 'test_user_1', 2); --2
INSERT INTO public.vehicle (id, vehicle_number, "owner", "type") VALUES(nextval('vehicle_id_seq'::regclass), 'MH12VV5072', 'test_user_1', 2); --3
INSERT INTO public.vehicle (id, vehicle_number, "owner", "type") VALUES(nextval('vehicle_id_seq'::regclass), 'MH12GH0001', 'test_user_2', 3); --4
INSERT INTO public.vehicle (id, vehicle_number, "owner", "type") VALUES(nextval('vehicle_id_seq'::regclass), 'MH12GH0002', 'test_user_3', 3); --5

INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available)  VALUES (1, 101, 1, 1, TRUE); --1
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available)  VALUES (1, 102, 1, 1, TRUE); --2
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available)  VALUES (1, 103, 1, 1, TRUE); --3
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available)  VALUES (1, 104, 1, 1, TRUE); --4
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available)  VALUES (1, 105, 1, 1, TRUE); --5
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available)  VALUES (1, 106, 1, 1, TRUE); --6
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available)  VALUES (1, 107, 1, 1, TRUE); --7
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available)  VALUES (1, 108, 1, 1, TRUE); --8
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available)  VALUES (1, 109, 1, 1, TRUE); --9
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available)  VALUES (1, 110, 1, 1, TRUE); --10
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 111, 2, 2, TRUE); --11
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 112, 3, 2, TRUE); --12
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 113, 2, 2, TRUE); --13
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 114, 3, 2, TRUE); --14
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 115, 2, 2, TRUE); --15
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 116, 3, 2, TRUE); --16
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 117, 2, 2, TRUE); --17
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 118, 3, 2, TRUE); --18
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 119, 2, 2, TRUE); --19
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 120, 3, 2, TRUE); --20
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 121, 3, 3, TRUE); --21
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 122, 2, 3, TRUE); --22
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 123, 3, 3, TRUE); --23
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 124, 2, 3, TRUE); --24
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 125, 3, 3, TRUE); --25
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 126, 2, 3, TRUE); --26
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 127, 3, 3, TRUE); --27
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 128, 2, 3, TRUE); --28
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 129, 3, 3, TRUE); --29
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) VALUES (1, 130, 2, 3, TRUE); --30

-- 1. Active session (Slot 12, Vehicle 3)
INSERT INTO parking (active, parking_slot, vehicle, payment_status, payment_amount, created_at, completed_at) 
VALUES (TRUE, 12, 3, FALSE, NULL, '2025-09-20 07:45:00+05:30', NULL);

-- 2. Completed, Paid session (Slot 25, Vehicle 1)
INSERT INTO parking (active, parking_slot, vehicle, payment_status, payment_amount, created_at, completed_at) 
VALUES (FALSE, 25, 1, TRUE, 12.50, '2025-09-20 10:00:00+05:30', '2025-09-20 11:15:00+05:30');

-- 3. Active session (Slot 5, Vehicle 5)
INSERT INTO parking (active, parking_slot, vehicle, payment_status, payment_amount, created_at, completed_at) 
VALUES (TRUE, 5, 5, FALSE, NULL, '2025-09-20 15:30:00+05:30', NULL);

-- 4. Completed, Failed payment session (Slot 30, Vehicle 2)
INSERT INTO parking (active, parking_slot, vehicle, payment_status, payment_amount, created_at, completed_at) 
VALUES (FALSE, 30, 2, FALSE, 25.00, '2025-09-19 23:10:00+05:30', '2025-09-20 04:40:00+05:30');

-- 5. Active session (Slot 8, Vehicle 4)
INSERT INTO parking (active, parking_slot, vehicle, payment_status, payment_amount, created_at, completed_at) 
VALUES (TRUE, 8, 4, FALSE, NULL, '2025-09-20 21:05:00+05:30', NULL);

-- 6. Completed, Paid session (Slot 17, Vehicle 3)
INSERT INTO parking (active, parking_slot, vehicle, payment_status, payment_amount, created_at, completed_at) 
VALUES (FALSE, 17, 3, TRUE, 42.99, '2025-09-19 14:20:00+05:30', '2025-09-19 20:10:00+05:30');

-- 7. Completed, Paid session (Slot 2, Vehicle 5)
INSERT INTO parking (active, parking_slot, vehicle, payment_status, payment_amount, created_at, completed_at) 
VALUES (FALSE, 2, 5, TRUE, 7.50, '2025-09-20 13:00:00+05:30', '2025-09-20 13:45:00+05:30');

-- 8. Active session (Slot 21, Vehicle 2)
INSERT INTO parking (active, parking_slot, vehicle, payment_status, payment_amount, created_at, completed_at) 
VALUES (TRUE, 21, 2, FALSE, NULL, '2025-09-20 18:45:00+05:30', NULL);

-- 9. Completed, Paid session (Slot 1, Vehicle 4)
INSERT INTO parking (active, parking_slot, vehicle, payment_status, payment_amount, created_at, completed_at) 
VALUES (FALSE, 1, 4, TRUE, 18.00, '2025-09-20 05:00:00+05:30', '2025-09-20 07:30:00+05:30');

-- 10. Completed, Failed payment session (Slot 19, Vehicle 1)
INSERT INTO parking (active, parking_slot, vehicle, payment_status, payment_amount, created_at, completed_at) 
VALUES (FALSE, 19, 1, FALSE, 5.25, '2025-09-20 16:55:00+05:30', '2025-09-20 17:05:00+05:30');

-- Assuming current time is 2025-09-20 23:31:03+05:30 IST

-- 1. Rule for Vehicle Type 1 (e.g., standard car)
INSERT INTO pricing_rule (name, description, created_at, active, vehicle_type, free_parking_hours, hourly_rate)
VALUES ('default_type_1', 'Standard rate for general parking (Type 1)', '2025-09-20 23:31:03+05:30', TRUE, 1, 1, 10.00);

-- 2. Rule for Vehicle Type 2 (e.g., compact/two-wheeler)
INSERT INTO pricing_rule (name, description, created_at, active, vehicle_type, free_parking_hours, hourly_rate)
VALUES ('default_type_2', 'Compact/Two-wheeler rate (Type 2)', '2025-09-20 23:31:03+05:30', TRUE, 2, 0, 15.00);

-- 3. Rule for Vehicle Type 3 (e.g., large vehicle/SUV)
INSERT INTO pricing_rule (name, description, created_at, active, vehicle_type, free_parking_hours, hourly_rate)
VALUES ('default_type_3', 'Premium/Large vehicle rate (Type 3)', '2025-09-20 23:31:03+05:30', TRUE, 3, 1, 20.00);


*/

-- parking lot --
INSERT INTO parking_lot (name, floors) 
VALUES ('small_mall_1', 2);

-- entry gates --
-- Assuming parking_lot ID 1 for "small_mall_1"

-- Gate 1 (Main Entrance)
INSERT INTO parking_lot_entry_gate (parking_lot, gate_number) 
VALUES (1, 1);

-- Gate 2 (Rear Entrance)
INSERT INTO parking_lot_entry_gate (parking_lot, gate_number) 
VALUES (1, 2);

-- slots --
-- Slot 101: Floor 1, Type 1, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 101, 1, 1, TRUE);

-- Slot 102: Floor 1, Type 1, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 102, 1, 1, TRUE);

-- Slot 103: Floor 1, Type 1, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 103, 1, 1, TRUE);

-- Slot 104: Floor 1, Type 2, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 104, 1, 2, TRUE);

-- Slot 105: Floor 1, Type 2, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 105, 1, 2, TRUE);

-- Slot 106: Floor 1, Type 2, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 106, 1, 2, TRUE);

-- Slot 107: Floor 1, Type 3, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 107, 1, 3, TRUE);

-- Slot 108: Floor 1, Type 3, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 108, 1, 3, TRUE);

-- Slot 109: Floor 1, Type 3, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 109, 1, 3, TRUE);

-- Slot 201: Floor 2, Type 1, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 201, 2, 1, TRUE);

-- Slot 202: Floor 2, Type 1, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 202, 2, 1, TRUE);

-- Slot 203: Floor 2, Type 1, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 203, 2, 1, TRUE);

-- Slot 204: Floor 2, Type 2, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 204, 2, 2, TRUE);

-- Slot 205: Floor 2, Type 2, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 205, 2, 2, TRUE);

-- Slot 206: Floor 2, Type 2, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 206, 2, 2, TRUE);

-- Slot 207: Floor 2, Type 3, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 207, 2, 3, TRUE);

-- Slot 208: Floor 2, Type 3, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 208, 2, 3, TRUE);

-- Slot 209: Floor 2, Type 3, Available
INSERT INTO parking_slot (parking_lot, slot_number, floor_number, parking_type, available) 
VALUES (1, 209, 2, 3, TRUE);

--entry distances

INSERT INTO entry_gate_distance (parking_lot_entry_gate, parking_slot, distance) VALUES
(1, 1, 1),  -- Gate 1 to Slot 101 (ID 1)
(1, 2, 2),  -- Gate 1 to Slot 102 (ID 2)
(1, 3, 3),  -- Gate 1 to Slot 103 (ID 3)
(1, 4, 4),  -- Gate 1 to Slot 104 (ID 4)
(1, 5, 5),  -- Gate 1 to Slot 105 (ID 5)
(1, 6, 6),  -- Gate 1 to Slot 106 (ID 6)
(1, 7, 7),  -- Gate 1 to Slot 107 (ID 7)
(1, 8, 8),  -- Gate 1 to Slot 108 (ID 8)
(1, 9, 9);  -- Gate 1 to Slot 109 (ID 9)

INSERT INTO entry_gate_distance (parking_lot_entry_gate, parking_slot, distance) VALUES
(1, 10, 10), -- Gate 1 to Slot 201 (ID 10)
(1, 11, 11), -- Gate 1 to Slot 202 (ID 11)
(1, 12, 12), -- Gate 1 to Slot 203 (ID 12)
(1, 13, 13), -- Gate 1 to Slot 204 (ID 13)
(1, 14, 14), -- Gate 1 to Slot 205 (ID 14)
(1, 15, 15), -- Gate 1 to Slot 206 (ID 15)
(1, 16, 16), -- Gate 1 to Slot 207 (ID 16)
(1, 17, 17), -- Gate 1 to Slot 208 (ID 17)
(1, 18, 18); -- Gate 1 to Slot 209 (ID 18)

INSERT INTO entry_gate_distance (parking_lot_entry_gate, parking_slot, distance) VALUES
(2, 18, 1),  -- Gate 2 to Slot 209 (ID 18)
(2, 17, 2),  -- Gate 2 to Slot 208 (ID 17)
(2, 16, 3),  -- Gate 2 to Slot 207 (ID 16)
(2, 15, 4),  -- Gate 2 to Slot 206 (ID 15)
(2, 14, 5),  -- Gate 2 to Slot 205 (ID 14)
(2, 13, 6),  -- Gate 2 to Slot 204 (ID 13)
(2, 12, 7),  -- Gate 2 to Slot 203 (ID 12)
(2, 11, 8),  -- Gate 2 to Slot 202 (ID 11)
(2, 10, 9);  -- Gate 2 to Slot 201 (ID 10)

INSERT INTO entry_gate_distance (parking_lot_entry_gate, parking_slot, distance) VALUES
(2, 9, 10),  -- Gate 2 to Slot 109 (ID 9)
(2, 8, 11),  -- Gate 2 to Slot 108 (ID 8)
(2, 7, 12),  -- Gate 2 to Slot 107 (ID 7)
(2, 6, 13),  -- Gate 2 to Slot 106 (ID 6)
(2, 5, 14),  -- Gate 2 to Slot 105 (ID 5)
(2, 4, 15),  -- Gate 2 to Slot 104 (ID 4)
(2, 3, 16),  -- Gate 2 to Slot 103 (ID 3)
(2, 2, 17),  -- Gate 2 to Slot 102 (ID 2)
(2, 1, 18);  -- Gate 2 to Slot 101 (ID 1)

-- creating parked vehicles

INSERT INTO vehicle (vehicle_number, owner, type) 
VALUES ('MH01AA7777', 'Rajesh Sharma', 3);

INSERT INTO vehicle (vehicle_number, owner, type) 
VALUES ('KA03BB1234', 'Priya Patel', 1);

INSERT INTO vehicle (vehicle_number, owner, type) 
VALUES ('GJ06CD5678', 'Sunita Rao', 2);

-- create parking --