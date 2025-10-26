package com.candidate.candidate_insight.repository;

import com.candidate.candidate_insight.model.Location;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocationRepository {

    private final JdbcTemplate jdbcTemplate;

    public LocationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Location> rowMapper = (rs, rowNum) ->
            new Location(
                    rs.getString("locationcode"),
                    rs.getString("locationname"),
                    rs.getString("locationaddress")
            );

    public List<Location> findAll() {
        return jdbcTemplate.query("SELECT * FROM location", rowMapper);
    }

    public Location findByCode(String code) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM location WHERE locationcode = ?",
                rowMapper,
                code
        );
    }

    public int save(Location location) {
        return jdbcTemplate.update(
                "INSERT INTO location (locationcode, locationname, locationaddress) VALUES (?, ?, ?)",
                location.getLocationcode(),
                location.getLocationname(),
                location.getLocationaddress()
        );
    }

    public int update(Location location) {
        return jdbcTemplate.update(
                "UPDATE location SET locationname = ?, locationaddress = ? WHERE locationcode = ?",
                location.getLocationname(),
                location.getLocationaddress(),
                location.getLocationcode()
        );
    }

    public int delete(String code) {
        return jdbcTemplate.update("DELETE FROM location WHERE locationcode = ?", code);
    }
}
