package com.candidate.candidate_insight.service.impl;

import com.candidate.candidate_insight.model.Employee;
import com.candidate.candidate_insight.repository.EmployeeRepository;
import com.candidate.candidate_insight.service.EmployeeService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Employee getEmployeeById(Integer empno) {
        return repository.findById(empno);
    }

    @Override
    public int createEmployee(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public int updateEmployee(Employee employee) {
        return repository.update(employee);
    }

    @Override
    public int deleteEmployee(Integer empno) {
        return repository.delete(empno);
    }
}
