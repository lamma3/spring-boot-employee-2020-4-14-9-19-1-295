package com.thoughtworks.springbootemployee.model.request;

import lombok.Data;

@Data
public class EmployeeRequest {
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;
    private Integer companyId;
}
