package com.candidate.candidate_insight.controller;

import com.candidate.candidate_insight.model.Department;
import com.candidate.candidate_insight.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // 🔹 GET all
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    // 🔹 GET by code
    @GetMapping("/{code}")
    public ResponseEntity<Department> getDepartmentByCode(@PathVariable String code) {
        Department dept = departmentService.getDepartmentByCode(code);
        if (dept == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dept);
    }

    // 🔹 CREATE
    @PostMapping
    public ResponseEntity<String> createDepartment(@RequestBody Department dept) {
        int rows = departmentService.createDepartment(dept);
        if (rows > 0) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Department created successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Failed to create department");
    }

    // 🔹 UPDATE
    @PutMapping("/{code}")
    public ResponseEntity<String> updateDepartment(@PathVariable String code, @RequestBody Department dept) {
        dept.setDepartmentcode(code);
        int rows = departmentService.updateDepartment(dept);
        if (rows > 0) {
            return ResponseEntity.ok("Department updated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Department not found");
    }

    // 🔹 DELETE
    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteDepartment(@PathVariable String code) {
        int rows = departmentService.deleteDepartment(code);
        if (rows > 0) {
            return ResponseEntity.ok("Department deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Department not found");
    }
}
