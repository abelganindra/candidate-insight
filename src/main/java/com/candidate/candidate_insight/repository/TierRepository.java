package com.candidate.candidate_insight.repository;

import com.candidate.candidate_insight.model.Tier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TierRepository {

    private final JdbcTemplate jdbcTemplate;

    public TierRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Tier> rowMapper = (rs, rowNum) ->
            new Tier(
                    rs.getInt("tiercode"),
                    rs.getString("tiername")
            );

    public List<Tier> findAll() {
        return jdbcTemplate.query("SELECT * FROM tier", rowMapper);
    }

    public Tier findByCode(Integer code) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM tier WHERE tiercode = ?",
                rowMapper,
                code
        );
    }

    public int save(Tier tier) {
        return jdbcTemplate.update(
                "INSERT INTO tier (tiercode, tiername) VALUES (?, ?)",
                tier.getTiercode(),
                tier.getTiername()
        );
    }

    public int update(Tier tier) {
        return jdbcTemplate.update(
                "UPDATE tier SET tiername = ? WHERE tiercode = ?",
                tier.getTiername(),
                tier.getTiercode()
        );
    }

    public int delete(Integer code) {
        return jdbcTemplate.update("DELETE FROM tier WHERE tiercode = ?", code);
    }
}
