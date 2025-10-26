package com.candidate.candidate_insight.service;

import com.candidate.candidate_insight.model.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Integer empno);
    int createEmployee(Employee employee);
    int updateEmployee(Employee employee);
    int deleteEmployee(Integer empno);
}
