package com.thoughtworks.springbootemployee.model.response;

import com.thoughtworks.springbootemployee.model.db.Employee;
import lombok.Data;

import java.util.List;

@Data
public class CompanyResponse {
    private Integer id;
    private String companyName;
    private Integer employeeNumber;
    private List<Employee> employees;
}
