package com.thoughtworks.springbootemployee.model.response;

import lombok.Data;

@Data
public class EmployeeResponse {
    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;
    private Integer companyId;
}
