package com.candidate.candidate_insight.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Integer empno;
    private String empname;
    private Integer tiercode;
    private String locationcode;
    private String departmentcode;
    private Integer supervisorcode;
    private BigDecimal salary;
    private LocalDate entrydate;
}
