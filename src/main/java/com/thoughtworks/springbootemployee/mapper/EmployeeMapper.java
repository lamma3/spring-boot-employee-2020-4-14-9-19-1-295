package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.model.db.Employee;
import com.thoughtworks.springbootemployee.model.request.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.response.EmployeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee employeeRequestToEmployee(EmployeeRequest employeeRequest);
    EmployeeResponse employeeToEmployeeResponse(Employee employee);
}
