package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.model.db.Employee;
import com.thoughtworks.springbootemployee.model.request.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.response.EmployeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mappings({
            @Mapping(target = "name", source = "employeeRequest.name"),
            @Mapping(target = "age", source = "employeeRequest.age"),
            @Mapping(target = "gender", source = "employeeRequest.gender"),
            @Mapping(target = "salary", source = "employeeRequest.salary"),
            @Mapping(target = "companyId", source = "employeeRequest.companyId"),
    })
    Employee employeeRequestToEmployee(EmployeeRequest employeeRequest);

    EmployeeResponse employeeToEmployeeResponse(Employee employee);
}
