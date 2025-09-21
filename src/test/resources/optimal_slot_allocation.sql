SELECT
    ps.id AS recommended_slot_id,
    ps.slot_number,
    ps.floor_number,
    egd.distance
FROM
    parking_slot ps
    
-- 1. Join to EntryGateDistance to get the distance data
JOIN
    entry_gate_distance egd ON ps.id = egd.parking_slot
    
-- 2. Join to Vehicle (temporary join to retrieve the required 'type' for filtering)
JOIN
    vehicle v ON v.id = :vehicle_id_param
    
-- 3. LEFT JOIN to Parking to determine if the slot is currently occupied
--    We only care about ACTIVE parking sessions
LEFT JOIN
    parking p ON p.parking_slot = ps.id AND p.active = TRUE
    
WHERE
    -- CONDITION A: Filter by the specific Entry Gate ID provided
    egd.parking_lot_entry_gate = :entry_gate_id_param
    
    -- CONDITION B: Ensure the slot is available
    -- An available slot MUST NOT have an ACTIVE record in the 'parking' table.
    -- If p.parking_slot IS NULL, no active session exists for this slot.
    AND p.parking_slot IS NULL
    
    -- CONDITION C: Ensure the slot type matches the vehicle type
    AND ps.parking_type = v.type

    -- CONDITION D: Filter by the general 'available' flag (good for initial filtering/status)
    AND ps.available = TRUE

ORDER BY
    -- ORDER BY: Sort by the shortest distance first
    egd.distance ASC
    
-- LIMIT 1: Only retrieve the single nearest available slot
LIMIT 1;