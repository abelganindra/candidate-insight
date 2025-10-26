package com.candidate.candidate_insight.controller;

import com.candidate.candidate_insight.model.Employee;
import com.candidate.candidate_insight.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employee> getAll() {
        return service.getAllEmployees();
    }

    @GetMapping("/{empno}")
    public Employee getById(@PathVariable Integer empno) {
        return service.getEmployeeById(empno);
    }

    @PostMapping
    public String create(@RequestBody Employee employee) {
        service.createEmployee(employee);
        return "Employee added successfully";
    }

    @PutMapping("/{empno}")
    public String update(@PathVariable Integer empno, @RequestBody Employee employee) {
        employee.setEmpno(empno);
        service.updateEmployee(employee);
        return "Employee updated successfully";
    }

    @DeleteMapping("/{empno}")
    public String delete(@PathVariable Integer empno) {
        service.deleteEmployee(empno);
        return "Employee deleted successfully";
    }
}
