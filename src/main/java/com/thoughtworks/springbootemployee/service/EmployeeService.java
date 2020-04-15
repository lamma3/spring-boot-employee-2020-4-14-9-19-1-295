package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee get(Integer employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public void create(Employee employee) {
        employeeRepository.add(employee);
    }

    public void delete(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId);
        employeeRepository.remove(employee);
    }

    public void update(Integer employeeId, Employee employeeUpdate) {
        Employee employee = employeeRepository.findById(employeeId);
        if (employeeUpdate.getName() != null) {
            employee.setName(employeeUpdate.getName());
        }
        if (employeeUpdate.getAge() != null) {
            employee.setAge(employeeUpdate.getAge());
        }
        if (employeeUpdate.getGender() != null) {
            employee.setGender(employeeUpdate.getGender());
        }
    }
}
