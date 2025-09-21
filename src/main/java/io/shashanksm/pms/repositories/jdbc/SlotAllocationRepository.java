package io.shashanksm.pms.repositories.jdbc;

import io.shashanksm.pms.dtos.RecommendedSlotResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public class SlotAllocationRepository {

    private final JdbcTemplate jdbcTemplate;

    // Use constructor injection for the JdbcTemplate
    public SlotAllocationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    // SQL Query (formatted for Java string blocks)
    private static final String FIND_NEAREST_AVAILABLE_SLOT_SQL = """
        SELECT
            ps.id AS recommended_slot_id,
            ps.slot_number,
            ps.floor_number,
            egd.distance
        FROM
            parking_slot ps
        JOIN
            entry_gate_distance egd ON ps.id = egd.parking_slot
        JOIN
            vehicle v ON v.id = ?
        LEFT JOIN
            parking p ON p.parking_slot = ps.id AND p.active = TRUE
        WHERE
            egd.parking_lot_entry_gate = ?
            AND p.parking_slot IS NULL
            AND ps.available = TRUE
            AND ps.parking_type = v.type
        ORDER BY
            egd.distance ASC
        LIMIT 1
        """;
    
    // RowMapper to map the ResultSet to the RecommendedSlotResult record
    private static final RowMapper<RecommendedSlotResult> ROW_MAPPER = (rs, rowNum) -> new RecommendedSlotResult(
        rs.getLong("recommended_slot_id"),
        rs.getInt("slot_number"),
        rs.getInt("floor_number"),
        rs.getInt("distance")
    );

    /**
     * Finds the nearest available and type-matching parking slot 
     * from a specific entry gate for a given vehicle.
     *
     * @param vehicleId The ID of the vehicle (to determine type).
     * @param entryGateId The ID of the entry gate (to determine distance).
     * @return An Optional containing the best slot result, or empty if none found.
     */
    public Optional<RecommendedSlotResult> findNearestAvailableSlot(Long vehicleId, Long entryGateId) {
        
        // Parameters are mapped to the '?' placeholders in the SQL:
        // 1st '?' = vehicleId (used in JOIN vehicle v ON v.id = ?)
        // 2nd '?' = entryGateId (used in WHERE egd.parking_lot_entry_gate = ?)
        
        try {
            RecommendedSlotResult result = jdbcTemplate.queryForObject(
                FIND_NEAREST_AVAILABLE_SLOT_SQL,
                ROW_MAPPER,
                vehicleId,
                entryGateId
            );
            return Optional.ofNullable(result);
            
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            // This exception is thrown by queryForObject if no results are found (due to LIMIT 1)
            return Optional.empty();
        }
    }
}