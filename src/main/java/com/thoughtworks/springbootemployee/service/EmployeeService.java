package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.db.Employee;
import com.thoughtworks.springbootemployee.model.request.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.response.EmployeeResponse;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeMapper employeeMapper;

    public List<EmployeeResponse> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.employeeListToEmployeeResponseList(employees);
    }

    public List<EmployeeResponse> getAll(Integer page, Integer pageSize) {
        List<Employee> employees = employeeRepository.findAll(PageRequest.of(page, pageSize)).getContent();
        return employeeMapper.employeeListToEmployeeResponseList(employees);
    }

    public EmployeeResponse get(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        return employeeMapper.employeeToEmployeeResponse(employee);
    }

    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.employeeRequestToEmployee(employeeRequest);
        System.out.println(employee);
        Employee createdEmployee = employeeRepository.save(employee);
        return employeeMapper.employeeToEmployeeResponse(createdEmployee);
    }

    public void delete(Integer employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public EmployeeResponse update(Integer employeeId, EmployeeRequest employeeRequest) {
        Employee employeeUpdate = employeeMapper.employeeRequestToEmployee(employeeRequest);
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if(employee == null) {
            return null;
        }

        if (employeeUpdate.getName() != null) {
            employee.setName(employeeUpdate.getName());
        }
        if (employeeUpdate.getAge() != null) {
            employee.setAge(employeeUpdate.getAge());
        }
        if (employeeUpdate.getGender() != null) {
            employee.setGender(employeeUpdate.getGender());
        }
        if (employeeUpdate.getSalary() != null) {
            employee.setSalary(employeeUpdate.getSalary());
        }
        Employee updatedEmployee = employeeRepository.save(employee);
        return employeeMapper.employeeToEmployeeResponse(updatedEmployee);
    }

    public List<EmployeeResponse> getByGender(String gender) {
        List<Employee> employees = employeeRepository.findAllByGenderIgnoreCase(gender);
        return employeeMapper.employeeListToEmployeeResponseList(employees);
    }
}
