package com.thoughtworks.springbootemployee.model.request;

import com.thoughtworks.springbootemployee.model.db.Employee;
import lombok.Data;

import java.util.List;

@Data
public class CompanyRequest {
    private String companyName;
    private Integer employeeNumber;
    private List<Employee> employees;
}
