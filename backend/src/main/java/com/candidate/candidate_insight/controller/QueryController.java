package com.candidate.candidate_insight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
public class QueryController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 5.4.1 Cumulative Salary
    @GetMapping("/api/query1/cumulative-salary")
    public List<Map<String, Object>> getCumulativeSalary() {
        String sql = """
            SELECT 
                e1.dept_code,
                e1.emp_no,
                e1.emp_name,
                (SELECT SUM(e2.salary)
                 FROM employee e2
                 WHERE e2.dept_code = e1.dept_code
                   AND e2.emp_no <= e1.emp_no) AS cumulative_salary
            FROM employee e1
            ORDER BY e1.dept_code, e1.emp_no;
            """;
        return jdbcTemplate.queryForList(sql);
    }

    // 5.4.2 Department Analysis by Location
    @GetMapping("/api/query2/department-analysis")
    public List<Map<String, Object>> getDepartmentAnalysis() {
        String sql = """
            WITH dept_counts AS (
                SELECT dept_code, COUNT(*) AS emp_count
                FROM employee
                GROUP BY dept_code
            ),
            max_emp_dept AS (
                SELECT dc.dept_code, dc.emp_count
                FROM dept_counts dc
                WHERE dc.emp_count = (SELECT MAX(emp_count) FROM dept_counts)
            ),
            min_salary_dept AS (
                SELECT dept_code, AVG(salary) AS avg_salary
                FROM employee
                GROUP BY dept_code
                HAVING AVG(salary) = (SELECT MIN(AVG(salary)) FROM employee GROUP BY dept_code)
            )
            SELECT 
                l.location_name,
                d1.dept_name AS dept_with_most_employees,
                m1.emp_count AS dept_employee_count,
                ROUND(AVG(e2.salary),2) AS avg_salary_of_lowest_dept
            FROM location l
            LEFT JOIN department d1 ON d1.location_id = l.location_id
            LEFT JOIN max_emp_dept m1 ON m1.dept_code = d1.dept_code
            LEFT JOIN min_salary_dept m2 ON m2.dept_code = d1.dept_code
            LEFT JOIN employee e2 ON e2.dept_code = m2.dept_code
            GROUP BY l.location_name, d1.dept_name, m1.emp_count
            ORDER BY l.location_name;
            """;
        return jdbcTemplate.queryForList(sql);
    }

    // 5.4.3 Salary Ranking with Salary Gap
    @GetMapping("/api/query3/salary-ranking")
    public List<Map<String, Object>> getSalaryRanking() {
        String sql = """
            SELECT 
                l.location_name,
                d.dept_name,
                e1.emp_name,
                p.position_name,
                e1.salary,
                (SELECT COUNT(DISTINCT e2.salary) 
                 FROM employee e2
                 WHERE e2.dept_code = e1.dept_code
                   AND e2.location_id = e1.location_id
                   AND e2.salary > e1.salary) + 1 AS salary_rank,
                CASE 
                    WHEN (SELECT MAX(e3.salary) 
                          FROM employee e3 
                          WHERE e3.dept_code = e1.dept_code
                            AND e3.location_id = e1.location_id
                            AND e3.salary < e1.salary) IS NULL THEN 0
                    ELSE e1.salary - (SELECT MAX(e3.salary) 
                                     FROM employee e3 
                                     WHERE e3.dept_code = e1.dept_code
                                       AND e3.location_id = e1.location_id
                                       AND e3.salary < e1.salary)
                END AS salary_gap
            FROM employee e1
            JOIN department d ON d.dept_code = e1.dept_code
            JOIN location l ON l.location_id = e1.location_id
            JOIN position p ON p.position_id = e1.position_id
            ORDER BY l.location_name, d.dept_name, e1.salary DESC;
            """;
        return jdbcTemplate.queryForList(sql);
    }
}
