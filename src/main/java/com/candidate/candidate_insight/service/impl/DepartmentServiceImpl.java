package com.candidate.candidate_insight.service.impl;

import com.candidate.candidate_insight.model.Department;
import com.candidate.candidate_insight.repository.DepartmentRepository;
import com.candidate.candidate_insight.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentByCode(String departmentCode) {
        try {
            return departmentRepository.findById(departmentCode);
        } catch (Exception e) {
            return null; // Return null if not found
        }
    }

    @Override
    public int createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public int updateDepartment(Department department) {
        return departmentRepository.update(department);
    }

    @Override
    public int deleteDepartment(String departmentCode) {
        return departmentRepository.delete(departmentCode);
    }
}
