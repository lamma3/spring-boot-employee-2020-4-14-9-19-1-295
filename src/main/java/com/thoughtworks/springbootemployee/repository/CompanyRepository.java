package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();

    @PostConstruct
    private void addCompanies() {
        List<Employee> employeesInSpring = new ArrayList<>();
        employeesInSpring.add(new Employee(10, "spring1", 20, "Male", 1000));
        employeesInSpring.add(new Employee(11, "spring2", 19, "Male", 2000));
        employeesInSpring.add(new Employee(12, "spring3", 15, "Male", 3000));

        List<Employee> employeesInBoot = new ArrayList<>();
        employeesInBoot.add(new Employee(13, "boot1", 16, "Male", 4000));
        employeesInBoot.add(new Employee(14, "boot2", 15, "Male", 5000));

        companies.add(new Company(0, "spring", 3, employeesInSpring));
        companies.add(new Company(1, "boot", 2, employeesInBoot));
    }
}
