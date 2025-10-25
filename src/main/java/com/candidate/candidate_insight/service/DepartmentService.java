package com.candidate.candidate_insight.service;

import com.candidate.candidate_insight.model.Department;

import java.util.List;

public interface DepartmentService {
        List<Department> getAllDepartments();

    Department getDepartmentByCode(String departmentCode);

    int createDepartment(Department department);

    int updateDepartment(Department department);

    int deleteDepartment(String departmentCode);
}
