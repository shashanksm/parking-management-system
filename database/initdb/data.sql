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


-- 1. Rule for Vehicle Type 1 (e.g., compact/two-wheeler)
INSERT INTO pricing_rule (name, description, created_at, active, vehicle_type, free_parking_hours, hourly_rate)
VALUES ('default_type_1', 'Compact/Two-wheeler rate (Type 1)', '2025-09-20 23:31:03+05:30', TRUE, 1, 0, 15.00);

-- 1. Rule for Vehicle Type 2 (e.g., standard car)
INSERT INTO pricing_rule (name, description, created_at, active, vehicle_type, free_parking_hours, hourly_rate)
VALUES ('default_type_2', 'Standard rate for general parking (Type 2)', '2025-09-20 23:31:03+05:30', TRUE, 2, 1, 10.00);

-- 3. Rule for Vehicle Type 3 (e.g., large vehicle/SUV)
INSERT INTO pricing_rule (name, description, created_at, active, vehicle_type, free_parking_hours, hourly_rate)
VALUES ('default_type_3', 'Premium/Large vehicle rate (Type 3)', '2025-09-20 23:31:03+05:30', TRUE, 3, 1, 20.00);