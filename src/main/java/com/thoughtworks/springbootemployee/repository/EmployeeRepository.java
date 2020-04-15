package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    @PostConstruct
    private void addEmployees() {
        employees.add(new Employee(0, "Xiaoming", 20, "Male"));
        employees.add(new Employee(1, "Xiaohong", 19, "Male"));
        employees.add(new Employee(2, "Xiaozhi", 15, "Male"));
        employees.add(new Employee(3, "Xiaogang", 16, "Male"));
        employees.add(new Employee(4, "Xiaoxia", 15, "Male"));
    }

    public List<Employee> findAll(Integer page, Integer pageSize) {
        int fromIndex;
        int toIndex;
        if (page == null || pageSize == null) {
            fromIndex = 0;
            toIndex = employees.size();
        } else {
            fromIndex = (page - 1) * pageSize;
            toIndex = Math.min((fromIndex + pageSize), employees.size());
        }
        return employees.subList(fromIndex, toIndex);
    }

    public Employee findById(Integer employeeId) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .orElse(null);
    }

    public void add(Employee employee) {
        employees.add(employee);
    }

    public void remove(Employee employee) {
        employees.remove(employee);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().toLowerCase().equals(gender.toLowerCase()))
                .collect(Collectors.toList());
    }
}
