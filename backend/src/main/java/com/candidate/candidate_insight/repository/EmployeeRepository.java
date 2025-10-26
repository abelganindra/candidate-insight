package com.candidate.candidate_insight.repository;

import com.candidate.candidate_insight.model.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Employee> rowMapper = (rs, rowNum) ->
            new Employee(
                    rs.getInt("empno"),
                    rs.getString("empname"),
                    rs.getObject("tiercode") != null ? rs.getInt("tiercode") : null,
                    rs.getString("locationcode"),
                    rs.getString("departmentcode"),
                    rs.getObject("supervisorcode") != null ? rs.getInt("supervisorcode") : null,
                    rs.getBigDecimal("salary"),
                    rs.getDate("entrydate") != null ? rs.getDate("entrydate").toLocalDate() : null
            );

    public List<Employee> findAll() {
        return jdbcTemplate.query("SELECT * FROM employee", rowMapper);
    }

    public Employee findById(Integer empno) {
        return jdbcTemplate.queryForObject("SELECT * FROM employee WHERE empno = ?", rowMapper, empno);
    }

    public int save(Employee emp) {
        return jdbcTemplate.update(
                "INSERT INTO employee (empno, empname, tiercode, locationcode, departmentcode, supervisorcode, salary, entrydate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                emp.getEmpno(),
                emp.getEmpname(),
                emp.getTiercode(),
                emp.getLocationcode(),
                emp.getDepartmentcode(),
                emp.getSupervisorcode(),
                emp.getSalary(),
                emp.getEntrydate()
        );
    }

    public int update(Employee emp) {
        return jdbcTemplate.update(
                "UPDATE employee SET empname=?, tiercode=?, locationcode=?, departmentcode=?, supervisorcode=?, salary=?, entrydate=? WHERE empno=?",
                emp.getEmpname(),
                emp.getTiercode(),
                emp.getLocationcode(),
                emp.getDepartmentcode(),
                emp.getSupervisorcode(),
                emp.getSalary(),
                emp.getEntrydate(),
                emp.getEmpno()
        );
    }

    public int delete(Integer empno) {
        return jdbcTemplate.update("DELETE FROM employee WHERE empno = ?", empno);
    }
}
