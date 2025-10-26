package com.candidate.candidate_insight.repository;


import com.candidate.candidate_insight.model.Department;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentRepository {

    private final JdbcTemplate jdbcTemplate;

    public DepartmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Department> rowMapper = (rs, rowNum) ->
            new Department(
                    rs.getString("departmentcode"),
                    rs.getString("departmentname")
            );

    public List<Department> findAll() {
        return jdbcTemplate.query("SELECT * FROM department", rowMapper);
    }

    public Department findById(String departmentCode) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM department WHERE departmentcode = ?",
                rowMapper,
                departmentCode
        );
    }

    public int save(Department dept) {
        return jdbcTemplate.update(
                "INSERT INTO department (departmentcode, departmentname) VALUES (?, ?)",
                dept.getDepartmentcode(), dept.getDepartmentname()
        );
    }

    public int update(Department dept) {
        return jdbcTemplate.update(
                "UPDATE department SET departmentname = ? WHERE departmentcode = ?",
                dept.getDepartmentname(), dept.getDepartmentcode()
        );
    }

    public int delete(String departmentCode) {
        return jdbcTemplate.update(
                "DELETE FROM department WHERE departmentcode = ?",
                departmentCode
        );
    }
}
