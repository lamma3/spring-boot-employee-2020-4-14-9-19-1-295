package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.model.db.Employee;
import com.thoughtworks.springbootemployee.model.request.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.response.EmployeeResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee employeeRequestToEmployee(EmployeeRequest employeeRequest);
    EmployeeResponse employeeToEmployeeResponse(Employee employee);
    List<EmployeeResponse> employeeListToEmployeeResponseList(List<Employee> employees);
}
